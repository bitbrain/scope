package nl.fontys.scope.core;

import com.badlogic.gdx.math.Vector3;

import java.security.SecureRandom;
import java.util.UUID;

import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.object.GameObjectType;
import nl.fontys.scope.util.Colors;

public class Arena {

    public static interface ArenaBoundRestrictor {
        void restrict(World world, GameObject object);
    }

    private static final int DEFAULT_ENERGY_COUNT =100;
    public static final float RADIUS = 140;
    private static final float OUTER_RADIUS = 100;

    private GameObjectFactory factory;

    private SecureRandom random;

    public final SpawnManager spawnManager;

    public Arena(GameObjectFactory factory, int playerCount) {
        this.factory = factory;
        this.spawnManager = new SpawnManager(playerCount, RADIUS);
        random = new SecureRandom(UUID.randomUUID().toString().getBytes());
    }

    public ArenaBoundRestrictor getRestrictor() {
        return restrictor;
    }

    public void setup() {
        GameObject sphere = factory.createSphere(60f);
        sphere.getColor().set(Colors.PRIMARY);
        sphere.getColor().a = 0.75f;
        Vector3 v = new Vector3(0f, 0f, 0f);
        for (int i = 0; i < DEFAULT_ENERGY_COUNT; ++i) {
            double angle = 360f * random.nextFloat();
            float r = (RADIUS - OUTER_RADIUS) + OUTER_RADIUS * random.nextFloat();
            v.set(r, 0f, 0f);
            v.rotate((float) (angle), 0f, 1f, 0f);
            angle = 360f * random.nextFloat();
            v.rotate((float)(angle), 0f, 0f, 1f);
            factory.createEnergy(v.x, v.y, v.z).getColor().set(Colors.PRIMARY);
        }
        spawnPlayer(PlayerManager.getCurrent());
    }

    private void spawnPlayer(Player player) {
        GameObject ship = player.getShip();
        Vector3 point = spawnManager.fetchAvailableSpawnPoint();
        ship.setPosition(point.x, point.y, point.z);
    }

    public class SpawnManager {

        private final int playerCount;

        private final float arenaRadius;

        private int lastUsed = -1;

        public SpawnManager(int playerCount, float arenaRadius) {
            this.playerCount = playerCount;
            this.arenaRadius = arenaRadius;
        }

        public Vector3 fetchAvailableSpawnPoint() {
            int spawnPos = lastUsed;
            while (spawnPos == lastUsed) {
                spawnPos = (int)(playerCount * random.nextFloat()) + 1;
            }
            float angle = 360f / (float)(playerCount * spawnPos);
            Vector3 point = new Vector3(arenaRadius / 2f, 0f, 0f);
            point.rotate(angle, 0f, 1f, 0f);
            lastUsed = spawnPos;
            return point;
        }
    }

    private ArenaBoundRestrictor restrictor = new ArenaBoundRestrictor() {
        @Override
        public void restrict(World world, GameObject object) {
            Vector3 pos = object.getPosition();
            if (pos.len() > RADIUS) {
                if (object.getType().equals(GameObjectType.SHOT)) {
                    world.remove(object);
                } else {
                    pos.setLength(RADIUS);
                }
            }
        }
    };
}
