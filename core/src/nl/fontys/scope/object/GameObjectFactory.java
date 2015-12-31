package nl.fontys.scope.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import aurelienribon.tweenengine.Tween;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.EnergyController;
import nl.fontys.scope.core.controller.ParticleEffectController;
import nl.fontys.scope.core.controller.PlanetController;
import nl.fontys.scope.core.controller.RingController;
import nl.fontys.scope.tweens.ColorTween;
import nl.fontys.scope.util.Colors;

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
        object.setScale(0.65f);
        object.getColor().set(0.75f, 0.75f, 0.75f, 1f);
        ParticleEffectController c = new ParticleEffectController(Assets.ParticleEffects.POWER);
        c.setOffset(-4.5f, 0f, 0f);
        world.addController(object, c);
        return object;
    }

    public GameObject createShot(GameObject ship) {
        GameObject object = world.createGameObject();
        Vector3 pos = new Vector3(ship.getPosition()).add(new Vector3(20f, 0f, 0f).mul(ship.getOrientation()));
        object.setPosition(pos.x, pos.y, pos.z);
        object.setVelocity(new Vector3(250f, 0f, 0f).mul(ship.getOrientation()));
        object.setOrientation(ship.getOrientation().x, ship.getOrientation().y, ship.getOrientation().z, ship.getOrientation().w);
        object.setScale(14f);
        object.setType(GameObjectType.SHOT);
        object.getColor().set(Colors.SECONDARY);
        object.setPhysics(false);
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
        object.getColor().set(Colors.PRIMARY);
        object.setType(GameObjectType.ENERGY);
        object.setPosition(x, y, z);
        object.setScale(6.5f);
        object.setPhysics(false);
        ParticleEffectController pec = new ParticleEffectController(Assets.ParticleEffects.ENERGY);
        world.addController(object, pec);
        world.addController(object, new EnergyController());
        return object;
    }
}
