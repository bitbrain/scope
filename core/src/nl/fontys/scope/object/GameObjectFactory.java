package nl.fontys.scope.object;

import nl.fontys.scope.core.World;

/**
 * Creates game objects
 */
public class GameObjectFactory {

    private World world;

    public GameObjectFactory(World world) {
        this.world = world;
    }

    public GameObject createShip(float x, float y, float z) {
        GameObject object = world.createGameObject();
        object.setType(GameObjectType.SHIP);
        return object;
    }
}
