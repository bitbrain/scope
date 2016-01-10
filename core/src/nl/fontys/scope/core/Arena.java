package nl.fontys.scope.core;

import com.badlogic.gdx.math.Vector3;

import java.security.SecureRandom;
import java.util.UUID;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.Config;
import nl.fontys.scope.core.controller.EnergyController;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.object.GameObjectType;
import nl.fontys.scope.tweens.ColorTween;
import nl.fontys.scope.util.Colors;

public class Arena {

    private Vector3 tmp = new Vector3();

    public static interface ArenaBoundRestrictor {
        void restrict(World world, GameObject object);
    }

    private GameObjectFactory factory;

    private SecureRandom random;

    public final SpawnManager spawnManager;

    public Arena(GameObjectFactory factory, int playerCount) {
        this.factory = factory;
        this.spawnManager = new SpawnManager(playerCount, Config.ARENA_RADIUS);
        random = new SecureRandom(UUID.randomUUID().toString().getBytes());
    }

    public ArenaBoundRestrictor getRestrictor() {
        return restrictor;
    }

    public void setup(PlayerManager playerManager, World world, TweenManager tweenManager) {
        GameObject sphere = factory.createSphere(60f);
        sphere.getColor().set(Colors.PRIMARY);
        for (int i = 0; i < Config.ENERGY_COUNT; ++i) {
            spawnRandomEnergy(world);
        }

        for (Player player : playerManager.getPlayers()) {
            spawnPlayer(player);
        }
    }

    public void spawnRandomEnergy(World world) {
        double angle = 360f * random.nextFloat();
        float r = (Config.ARENA_RADIUS - Config.ARENA_OUTER_RADIUS) + Config.ARENA_OUTER_RADIUS * random.nextFloat();
        tmp.set(r, 0f, 0f);
        tmp.rotate((float) (angle), 0f, 1f, 0f);
        angle = 360f * random.nextFloat();
        tmp.rotate((float) (angle), 0f, 0f, 1f);
        GameObject energy = factory.createEnergy(tmp.x, tmp.y, tmp.z);
        energy.getColor().set(Colors.PRIMARY);
        world.addController(energy, new EnergyController());
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
            if (pos.len() > Config.ARENA_RADIUS) {
                if (object.getType().equals(GameObjectType.SHOT)) {
                    world.remove(object);
                } else if (object.getType().equals(GameObjectType.ENERGY)){
                    pos.setLength(Config.ARENA_RADIUS);
                    Vector3 velocity = object.getVelocity();
                    velocity.setToRandomDirection();
                } else {
                    pos.setLength(Config.ARENA_RADIUS);
                }
            } else if (pos.len() < Config.ARENA_RADIUS - Config.ARENA_OUTER_RADIUS) {
                if (object.getType().equals(GameObjectType.ENERGY)){
                    pos.setLength(Config.ARENA_RADIUS - Config.ARENA_OUTER_RADIUS);
                    Vector3 velocity = object.getVelocity();
                    velocity.setToRandomDirection();
                }
            }
        }
    };
}
