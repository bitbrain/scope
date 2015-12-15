package nl.fontys.scope.core;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import net.engio.mbassy.listener.Handler;

import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.object.GameObjectType;

public class GameLogicHandler implements Disposable {

    private static final int POINTS_PER_ENERGY = 150;

    private Events events = Events.getInstance();

    private World world;

    private GameObjectFactory factory;

    private Arena arena;

    private final PlayerManager playerManager;

    public GameLogicHandler(World world, GameObjectFactory factory, Arena arena, PlayerManager playerManager) {
        events.register(this);
        this.playerManager = playerManager;
        this.world = world;
        this.factory = factory;
        this.arena = arena;
    }

    @Handler
    public void handle(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.COLLISION)) {
            handleCollision((GameObject)event.getPrimaryParam(), (GameObject)event.getSecondaryParam(0), false);
        } else if (event.isTypeOf(EventType.COLLISION_FULL)) {
            handleCollision((GameObject)event.getPrimaryParam(), (GameObject)event.getSecondaryParam(0), true);
        }
    }

    @Override
    public void dispose() {
        events.unregister(this);
    }

    private void handleCollision(GameObject objectA, GameObject objectB, boolean full) {
        Player currentPlayer = PlayerManager.getCurrent();
        boolean isCurrentShip = objectA.equals(currentPlayer.getShip());
        if (isCurrentShip && GameObjectType.ENERGY.equals(objectB.getType())) {
            currentPlayer.addEnergy();
            world.remove(objectB);
        } else if (full && isCurrentShip && GameObjectType.SPHERE.equals(objectB.getType())) {
            currentPlayer.addPoints(currentPlayer.dropEnergy() * POINTS_PER_ENERGY);
        } else if (isCurrentShip && GameObjectType.SHIP.equals(objectB.getType())) {
            destroyShip(objectA);
            destroyShip(objectB);
        } else if (GameObjectType.SHOT.equals(objectA.getType()) && GameObjectType.SPHERE.equals(objectB.getType())) {
            world.remove(objectA);
        }  else if (GameObjectType.SHOT.equals(objectA.getType()) && GameObjectType.SHIP.equals(objectB.getType())) {
            destroyShip(objectB);
            world.remove(objectA);
        }
    }

    private void destroyShip(GameObject object) {
        Player player = playerManager.getPlayerByShip(object);
        if (player != null) {
            Vector3 currentPos = object.getPosition();
            Vector3 pos = arena.spawnManager.fetchAvailableSpawnPoint();
            int energyCount = player.dropEnergy();
            for (int i = 0; i < energyCount; ++i) {
                factory.createEnergy(currentPos.x, currentPos.y, currentPos.z);
            }
            object.setPosition(pos.x, pos.y, pos.z);
        }
    }
}
