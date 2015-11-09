package nl.fontys.scope.core.controller;

import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.controls.Moveable;
import nl.fontys.scope.core.GameObject;

public class ShipController implements GameObjectController, Moveable {

    public static final float SPEED = 4f;

    private Vector3 accel = new Vector3();

    @Override
    public void update(GameObject object, float delta) {
        object.getVelocity().x += accel.x;
        object.getVelocity().y += accel.y;
        object.getVelocity().z += accel.z;
    }

    @Override
    public void moveLeft() {
        accel.y = -SPEED;
    }

    @Override
    public void moveRight() {
        accel.y = SPEED;
    }



    @Override
    public void moveForward() {
        accel.x = -SPEED;
    }

    @Override
    public void moveUp() {
        accel.z = SPEED / 2f;
    }
}
