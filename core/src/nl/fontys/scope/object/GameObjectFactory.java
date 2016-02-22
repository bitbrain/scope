package nl.fontys.scope.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.logic.LightingLogic;
import nl.fontys.scope.core.logic.ParticleEffectLogic;
import nl.fontys.scope.util.Colors;
import nl.fontys.scope.util.Mutator;

/**
 * Creates game objects
 */
public class GameObjectFactory {

    private class GameObjectMutator implements Mutator<GameObject> {

        private GameObject reference;

        public GameObjectMutator() {
            reference = new GameObject();
            reference.reset();
        }

        public GameObject getReference() {
            return reference;
        }

        @Override
        public void mutate(GameObject target) {
            target.set(reference);
            reference.reset();
        }
    }

    private GameObjectMutator mutator = new GameObjectMutator();

    private World world;

    public GameObjectFactory(World world) {
        this.world = world;
    }

    public GameObject createShip(float x, float y, float z) {
        GameObject reference = mutator.getReference();
        reference.setType(GameObjectType.SHIP);
        reference.setCollisionScale(0f);
        reference.setScale(1.65f);
        reference.getColor().set(0.75f, 0.75f, 0.75f, 1f);
        GameObject object = world.createGameObject(mutator);
        ParticleEffectLogic c = new ParticleEffectLogic(Assets.ParticleEffects.POWER);
        final float X_OFFSET = -3.2f;
        c.setOffset(X_OFFSET, 0f, 0f);
        LightingLogic lc = new LightingLogic(world.getLightingManager(), Colors.SECONDARY);
        lc.setStrength(7);
        lc.setOffset(X_OFFSET, 0f, 0f);
        world.addLogic(object, c);
        world.addLogic(object, lc);
        return object;
    }

    public GameObject createShot(GameObject ship) {
        GameObject reference = mutator.getReference();
        Vector3 pos = new Vector3(ship.getPosition()).add(new Vector3(20f, 0f, 0f).mul(ship.getOrientation()));
        reference.setExternalId(ship.getId());
        reference.setPosition(pos.x, pos.y, pos.z);
        reference.setVelocity(new Vector3(250f, 0f, 0f).mul(ship.getOrientation()));
        reference.setOrientation(ship.getOrientation().x, ship.getOrientation().y, ship.getOrientation().z, ship.getOrientation().w);
        reference.setScale(14f);
        reference.setType(GameObjectType.SHOT);
        reference.getColor().set(Colors.SECONDARY);
        reference.setPhysics(false);
        return world.createGameObject(mutator);
    }

    public GameObject createPlanet(float scale) {
        GameObject reference = mutator.getReference();
        reference.setType(GameObjectType.PLANET);
        reference.getColor().set(0.6f, 0.2f, 0.5f, 1f);
        reference.setScale(scale);
        return world.createGameObject(mutator);
    }

    public GameObject createSphere(float scale) {
        GameObject reference = mutator.getReference();
        reference.setType(GameObjectType.SPHERE);
        reference.getColor().set(0.6f, 0.2f, 0.5f, 1f);
        reference.setScale(scale);
        reference.setCollisionScale(0f);
        GameObject object = world.createGameObject(mutator);
        world.addLogic(object, new ParticleEffectLogic(Assets.ParticleEffects.SPHERE));
        return object;
    }

    public GameObject createEnergy(float x, float y, float z) {
        GameObject reference = mutator.getReference();
        reference.getColor().set(Colors.PRIMARY);
        reference.setType(GameObjectType.ENERGY);
        reference.setPosition(x, y, z);
        reference.setCollisionScale(2f);
        reference.setScale(2.5f);
        reference.setPhysics(false);

        GameObject object = world.createGameObject(mutator);
        world.addLogic(object, new ParticleEffectLogic(Assets.ParticleEffects.ENERGY));
        return object;
    }
}
