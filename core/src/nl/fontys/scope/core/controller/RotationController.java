package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

public class RotationController implements GameObjectController {

    private float angle;

    private float radius;

    private float speed;

    public RotationController(float radius, float angle, float speed) {
        this.radius = radius;
        this.angle = angle;
        this.speed = speed;
    }

    @Override
    public void update(GameObject object, float delta) {
        Vector3 pos = object.getPosition();
        pos.x = (float)Math.cos(Math.toRadians(angle)) * radius;
        pos.z = (float)Math.sin(Math.toRadians(angle)) * radius;
        angle += speed * delta;
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }
}
