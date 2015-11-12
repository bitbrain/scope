package nl.fontys.scope.core.controller;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.controls.Moveable;
import nl.fontys.scope.object.GameObject;

public class ShipController implements GameObjectController, Moveable {

    public static final float SPEED = 4f;

    private Vector3 accel = new Vector3();

    private float angle = 0;

    @Override
    public void update(GameObject object, float delta) {
        Quaternion q = object.getOrientation();
        accel.z = 0;
        accel.rotate(q.getAngleAround(Vector3.X), 1f, 0f, 0f);
        accel.rotate(q.getAngleAround(Vector3.Y), 0f, 1f, 0f);
        accel.rotate(q.getAngleAround(Vector3.Z), 0f, 0f, 1f);
        object.getVelocity().x += accel.x;
        object.getVelocity().y += accel.y;
        object.getVelocity().z += accel.z;
        q.set(Vector3.Y, q.getAngleAround(Vector3.Y)  + angle);
        accel.scl(0.7f);
        angle *= 0.98f;
    }

    @Override
    public void moveLeft() {
        angle += 0.1f;
    }

    @Override
    public void moveRight() {
        angle += -0.1f;
    }

    @Override
    public void moveForward() {
        accel.x = SPEED;
    }

    @Override
    public void moveBack() {
        accel.x = -SPEED;
    }

    @Override
    public void moveUp() {
        accel.y = SPEED;
    }

    @Override
    public void moveDown() {
        accel.y = -SPEED;
    }
}
