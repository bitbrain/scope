package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

public class PlanetController implements GameObjectController {

    private float angle;

    private float radius;

    public PlanetController(float radius) {
        this.radius = radius;
    }

    @Override
    public void update(GameObject object, float delta) {
        Vector3 pos = object.getPosition();
        pos.x = (float)Math.cos(Math.toRadians(angle)) * radius;
        pos.z = (float)Math.sin(Math.toRadians(angle)) * radius;
        angle += 1.4f * delta;
    }
}
