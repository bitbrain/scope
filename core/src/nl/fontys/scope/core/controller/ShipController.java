package nl.fontys.scope.core.controller;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.Config;
import nl.fontys.scope.controls.Moveable;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.object.GameObject;

public class ShipController implements GameObjectController, Moveable {

    private Vector3 accel = new Vector3();

    private float angle = 0;

    private Vector3 rotation = new Vector3();

    Matrix4 m = new Matrix4();

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
        m.set(q);
        m.rotate(Vector3.X, rotation.x);
        m.rotate(Vector3.Y, rotation.y);
        m.rotate(Vector3.Z, rotation.z);
        q.setFromMatrix(m);
        accel.scl(0.9f);
        rotation.scl(0.9f);
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }

    @Override
    public void rotate(float yaw, float pitch, float roll) {
        rotation.x = yaw;
        rotation.y = pitch;
        rotation.z = roll;
    }

    @Override
    public void rise(float factor) {
        accel.y = factor * 3f;
    }

    @Override
    public void boost(float strength) {
        accel.x = strength * 6f;
    }

    @Override
    public void shoot() {
        PlayerManager.getCurrent().getWeapon().shoot();
    }
}
