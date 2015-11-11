package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.core.GameObject;

public class CameraController {

    private GameObject target;
    private PerspectiveCamera cam;

    public CameraController(GameObject target, PerspectiveCamera cam) {
        this.target = target;
        this.cam = cam;
        Vector3 pos = target.getPosition();
    }

    public void update(float delta) {
        Vector3 pos = target.getPosition();
        float camX = (float)(pos.x + Math.cos(-target.getOrientation().getAngleAroundRad(Vector3.Y)) * (-15f));
        float camZ = (float)(pos.z + Math.sin(-target.getOrientation().getAngleAroundRad(Vector3.Y)) * (-15f));
        cam.position.set(camX, pos.y + 8, camZ);
        cam.lookAt(pos.x, pos.y + 4, pos.z);
    }
}
