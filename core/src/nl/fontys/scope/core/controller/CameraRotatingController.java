package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.object.GameObject;

public class CameraRotatingController implements GameObjectController {

    private PerspectiveCamera cam;

    private GameObject target;

    private final float radius;

    private float angle;

    public CameraRotatingController(float radius, PerspectiveCamera cam, GameObject object) {
        this.cam = cam;
        this.target = object;
        this.radius = radius;
        this.cam.position.set(radius, 0f, 0f);
    }

    @Override
    public void update(GameObject object, float delta) {
        Vector3 targetPos = target.getPosition();
        final float x = targetPos.x + radius * (float)Math.cos(Math.toRadians(angle));
        final float z = targetPos.z + radius * (float)Math.sin(Math.toRadians(angle));
        this.cam.position.set(x, 0f, z);
        this.cam.lookAt(object.getPosition().x, object.getPosition().y, object.getPosition().z);
        angle += 2f * delta;
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }
}
