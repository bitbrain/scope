package nl.fontys.scope.object;

import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.RingController;

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
        object.setScale(0.5f);
        return object;
    }

    public GameObject[] createArena(float x, float y, float z, int elements, float minScale, float maxScale) {
        GameObject[] rings = new GameObject[elements];
        float scaleStep = (maxScale - minScale) / elements;
        for (int i = 0; i < elements; ++i) {
            rings[i] = world.createGameObject();
            rings[i].setType(GameObjectType.RING);
            rings[i].setPosition(x, y, z);
            world.addController(rings[i], new RingController());
            rings[i].setScale(minScale + scaleStep * i);
        }
        return rings;
    }

    public GameObject createEnergy(float x, float y, float z) {
        GameObject object = world.createGameObject();
        object.setType(GameObjectType.ENERGY);
        object.setPosition(x, y, z);
        object.setScale(4.5f);
        return object;
    }
}
