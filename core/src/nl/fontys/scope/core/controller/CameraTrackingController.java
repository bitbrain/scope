package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.object.GameObject;

public class CameraTrackingController implements GameObjectController {

    private static final float CAMERA_HEIGHT = 7f;
    private static final float CAMERA_DISTANCE = 12f;

    private static final float FRONT_OFFSET = 30f;

    private PerspectiveCamera cam;

    private Vector3 v = new Vector3();

    private Matrix4 m = new Matrix4();

    public CameraTrackingController(PerspectiveCamera cam) {
        this.cam = cam;
    }

    @Override
    public void update(GameObject object, float delta) {
        Vector3 pos = object.getPosition();
        m.toNormalMatrix();
        m.set(object.getPosition(), object.getOrientation());
        v.set(FRONT_OFFSET, 0f, 0f);
        v.rot(m);
        v.add(pos);
        cam.lookAt(v.x, v.y, v.z);
        v.set(-CAMERA_DISTANCE, CAMERA_HEIGHT, 0f);
        v.rot(m);
        v.add(pos);
        cam.position.set(v);
        v.set(0f, 1f, 0f);
        v.rot(m);
        cam.up.set(v);
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }
}
