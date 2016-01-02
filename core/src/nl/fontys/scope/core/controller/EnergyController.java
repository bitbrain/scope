package nl.fontys.scope.core.controller;

import com.badlogic.gdx.math.Vector3;

import java.security.SecureRandom;
import java.util.UUID;

import nl.fontys.scope.Config;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

public class EnergyController implements GameObjectController {

    private Vector3 velocity = new Vector3();

    private Vector3 distance = new Vector3();

    private SecureRandom random = new SecureRandom(UUID.randomUUID().toString().getBytes());

    public EnergyController() {
        velocity.x = random.nextFloat() * Config.MAX_ENERGY_SPEED;
        velocity.y = random.nextFloat() * Config.MAX_ENERGY_SPEED;
        if (velocity.len() > Config.MAX_ENERGY_SPEED) {
            velocity.setLength(Config.MAX_ENERGY_SPEED);
        }
    }

    @Override
    public void update(GameObject object, float delta) {
        if (random.nextFloat() > 0.1f) {
            velocity.x += random.nextFloat() < 0.5f ? -random.nextFloat() : random.nextFloat();
        }
        if (random.nextFloat() > 0.1f) {
            velocity.y += random.nextFloat() < 0.5f ? -random.nextFloat() : random.nextFloat();
        }
        if (random.nextFloat() > 0.1f) {
            velocity.z += random.nextFloat() < 0.5f ? -random.nextFloat() : random.nextFloat();
        }
        if (velocity.len() > Config.MAX_ENERGY_SPEED) {
            velocity.setLength(Config.MAX_ENERGY_SPEED);
        }
        object.setVelocity(velocity);
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        if (GameObjectType.SHIP.equals(other.getType())) {
            distance.x = other.getPosition().x - object.getPosition().x;
            distance.y = other.getPosition().y - object.getPosition().y;
            distance.z = other.getPosition().z - object.getPosition().z;
            if (distance.len() < 30f) {
                distance.setLength(10f / distance.len());
                object.getVelocity().add(distance);
            } else if (distance.len() < 100f) {
                distance.setLength(2f / distance.len());
                object.getVelocity().add(distance);
            }
        }
    }
}
