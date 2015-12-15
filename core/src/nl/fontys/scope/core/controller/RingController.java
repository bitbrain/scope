package nl.fontys.scope.core.controller;

import java.security.SecureRandom;

import nl.fontys.scope.object.GameObject;

/**
 * Controls the random rotation of rings
 */
public class RingController implements GameObjectController {

    private SecureRandom random = new SecureRandom();

    private float yaw, pitch, roll;

    private float veloY, veloP, veloR;

    public RingController() {
        yaw = random.nextFloat() * 360f;
        pitch = random.nextFloat() * 360f;
        roll = random.nextFloat() * 360f;
        veloY = 1f + (float)random.nextFloat() * 5f;
        veloP = 1f + (float)random.nextFloat() * 5f;
        veloR = 1f + (float)random.nextFloat() * 5f;
    }
    @Override
    public void update(GameObject object, float delta) {
        object.getOrientation().setEulerAngles(yaw, pitch, roll);
        yaw += veloY * delta;
        pitch += veloP * delta;
        roll += veloR * delta;
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }
}
