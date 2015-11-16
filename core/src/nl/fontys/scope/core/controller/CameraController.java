package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

public class CameraController implements GameObjectController {

    private PerspectiveCamera cam;

    private Vector3 velocity = new Vector3();

    public CameraController(PerspectiveCamera cam) {
        this.cam = cam;
        this.cam.position.x = -8000f;
    }

    @Override
    public void update(GameObject object, float delta) {
        Vector3 pos = object.getPosition();
        float camX = (float)(pos.x + Math.cos(-object.getOrientation().getAngleAroundRad(Vector3.Y)) * (-15f));
        float camZ = (float)(pos.z + Math.sin(-object.getOrientation().getAngleAroundRad(Vector3.Y)) * (-15f));
        velocity.x = camX - cam.position.x;
        velocity.y = pos.y - cam.position.y;
        velocity.z = camZ - cam.position.z;
        float distance = velocity.len();
        velocity.nor();
        double speed = distance * 10.2f;
        cam.position.set(cam.position.x + (float)(velocity.x * speed * delta), pos.y + 2, cam.position.z + (float)(velocity.z * speed * delta));
        cam.lookAt(pos.x, pos.y + 2, pos.z);
    }
}
