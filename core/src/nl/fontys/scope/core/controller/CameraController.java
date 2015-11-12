package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

public class CameraController implements GameObjectController {

    private PerspectiveCamera cam;

    public CameraController(PerspectiveCamera cam) {
        this.cam = cam;
    }

    @Override
    public void update(GameObject object, float delta) {
        Vector3 pos = object.getPosition();
        float camX = (float)(pos.x + Math.cos(-object.getOrientation().getAngleAroundRad(Vector3.Y)) * (-15f));
        float camZ = (float)(pos.z + Math.sin(-object.getOrientation().getAngleAroundRad(Vector3.Y)) * (-15f));
        cam.position.set(camX, pos.y + 2, camZ);
        cam.lookAt(pos.x, pos.y + 2, pos.z);
    }
}
