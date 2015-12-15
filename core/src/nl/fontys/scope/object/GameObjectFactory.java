package nl.fontys.scope.object;

import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.PlanetController;
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
        object.getColor().set(0.65f, 0.65f, 0.65f, 1f);
        return object;
    }

    public GameObject createPlanet(float radius, float scale, float angle, float speed) {
        GameObject object = world.createGameObject();
        object.setType(GameObjectType.PLANET);
        object.setScale(scale);
        world.addController(object, new PlanetController(radius, angle, speed));
        return object;
    }

    public GameObject createSphere(float scale) {
        GameObject sphere = createPlanet(0f, scale, 0f, 0f);
        sphere.setType(GameObjectType.SPHERE);
        return sphere;
    }

    public GameObject createEnergy(float x, float y, float z) {
        GameObject object = world.createGameObject();
        object.setType(GameObjectType.ENERGY);
        object.setPosition(x, y, z);
        object.setScale(4.5f);
        return object;
    }
}
