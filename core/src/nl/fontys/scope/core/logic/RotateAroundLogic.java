package nl.fontys.scope.core.logic;

import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

public class RotateAroundLogic implements Logic {

    private float speed;

    private Vector3 angle = new Vector3();

    public RotateAroundLogic(float speed) {
        this.speed = speed;
        angle.setToRandomDirection().setLength(speed);

    }

    @Override
    public void update(GameObject object, float delta) {
        Vector3 pos = object.getPosition();
        pos.rotate(Vector3.X, angle.x * delta);
        pos.rotate(Vector3.Y, angle.y * delta);
        pos.rotate(Vector3.Z, angle.z * delta);

        angle.rotate(Vector3.X, angle.y * 2f * delta);
        angle.rotate(Vector3.Y, angle.z * 2f * delta);
        angle.rotate(Vector3.Z, angle.x * 2f * delta);
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }
}
