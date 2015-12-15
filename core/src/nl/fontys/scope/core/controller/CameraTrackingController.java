package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.object.GameObject;

public class CameraTrackingController implements GameObjectController {

    private PerspectiveCamera cam;

    private Vector3 velocity = new Vector3();

    public CameraTrackingController(PerspectiveCamera cam) {
        this.cam = cam;
        this.cam.position.x = -8000f;
    }

    @Override
    public void update(GameObject object, float delta) {
        Vector3 pos = object.getPosition();
        double camX = pos.x + Math.cos(-object.getOrientation().getAngleAroundRad(Vector3.Y)) * (-15f);
        double camZ = pos.z + Math.sin(-object.getOrientation().getAngleAroundRad(Vector3.Y)) * (-15f);
        velocity.x = (float)camX - cam.position.x;
        velocity.y = pos.y - cam.position.y;
        velocity.z = (float)camZ - cam.position.z;
        double distance = velocity.len();
        velocity.nor();
        double speed = distance * 20.2f;
        ShaderManager shaderManager = ShaderManager.getBaseInstance();
        shaderManager.zoomer.setBlurStrength((float)(speed*speed) * 0.000002f);
        shaderManager.zoomer.setZoom(1f + (float)(speed*speed) * 0.000001f);
        cam.position.set(cam.position.x + (float)(velocity.x * speed * delta), pos.y + 2, cam.position.z + (float)(velocity.z * speed * delta));
        cam.lookAt(pos.x, pos.y + 2, pos.z);
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }
}
