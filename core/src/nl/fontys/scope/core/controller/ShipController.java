package nl.fontys.scope.core.controller;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.Config;
import nl.fontys.scope.controls.Moveable;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.object.GameObject;

public class ShipController implements GameObjectController, Moveable {

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
        accel.scl(0.9f);
        angle *= 0.95f;
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }

    @Override
    public void rotate(float yaw, float pitch, float roll) {
        // TODO
    }

    @Override
    public void rise(float factor) {
        // TODO
    }

    @Override
    public void boost() {
        // TODO
    }

    @Override
    public void shoot() {
        PlayerManager.getCurrent().getWeapon().shoot();
    }
}
