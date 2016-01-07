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

        m = m.toNormalMatrix();
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
        accel.y = factor * 2f;
    }

    @Override
    public void boost(float strength) {
        accel.x = strength * 3f;
    }

    @Override
    public void shoot() {
        PlayerManager.getCurrent().getWeapon().shoot();
    }
}
