package nl.fontys.scope.core.controller;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.Config;
import nl.fontys.scope.controls.Moveable;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.object.GameObject;

public class ShipController implements GameObjectController, Moveable {

    private Vector3 accel = new Vector3(), v = new Vector3();

    private Vector3 rotation = new Vector3();

    Matrix4 m = new Matrix4();

    @Override
    public void update(GameObject object, float delta) {
        Quaternion q = object.getOrientation();
        m.set(object.getPosition(), q);
        m.rotate(Vector3.X, rotation.x);
        m.rotate(Vector3.Y, rotation.y);
        m.rotate(Vector3.Z, rotation.z);
        q.setFromMatrix(true, m);

        v.set(accel.x, accel.y, accel.z);
        v.rot(m);
        object.getVelocity().add(v);

        accel.scl(0.9f);
        rotation.scl(0.9f);
        if (accel.len() < 0.1f) {
            accel.setLength(0f);
        }
        if (rotation.len() < 0.1f) {
            rotation.setLength(0f);
        }
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }

    @Override
    public void rotate(float yaw, float pitch, float roll) {
        rotation.x += yaw / 10f;
        rotation.y += pitch / 10f;
        rotation.z += roll / 10f;
    }

    @Override
    public void rise(float factor) {
        accel.y = factor * 1f;
    }

    @Override
    public void boost(float strength) {
        accel.x = strength * 2f;
    }

    @Override
    public void shoot() {
        PlayerManager.getCurrent().getWeapon().shoot();
    }
}
