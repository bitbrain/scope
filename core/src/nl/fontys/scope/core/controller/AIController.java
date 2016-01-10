package nl.fontys.scope.core.controller;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

public class AIController implements GameObjectController {

    private GameObject target;

    private float lastTargetDistance, lastFindDistance = Float.MAX_VALUE;

    private Vector3 tmp = new Vector3();

    private Matrix4 mat = new Matrix4();

    private Player player;

    public AIController(Player player) {
        this.player = player;
    }

    @Override
    public void update(GameObject object, float delta) {
        if (target != null) {
            if (GameObjectType.SHIP.equals(target.getType())) {
                handleShip(player.getShip());
            } else if (GameObjectType.ENERGY.equals(target.getType())) {
                handleEnergy(player.getShip());
            }  else if (GameObjectType.SPHERE.equals(target.getType())) {
                handleSphere(player.getShip());
            }
        }
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        if (player.getFocusProgress() > 1f && other.getType().equals(GameObjectType.SPHERE)) {
            target = other;
        } else if (target == null && getDistanceTo(other) < lastFindDistance) {
            lastFindDistance = getDistanceTo(other);
            target = other;
        }
    }

    private void handleShip(GameObject thisObject) {
        final float MIN_SHIP_DISTANCE = 80f;
        final float MAX_SHIP_DISTANCE = 200f;
        if (lastTargetDistance > MIN_SHIP_DISTANCE) {
            moveToTarget(thisObject, target);
        }
        if (lastTargetDistance >= MAX_SHIP_DISTANCE) {
            target = null;
        }
        player.getWeapon().shoot();
    }

    private void handleEnergy(GameObject thisObject) {
        moveToTarget(thisObject, target);
        if (lastTargetDistance < 40f) {
            target = null;
        }
    }

    private void handleSphere(GameObject thisObject) {
        moveToTarget(thisObject, target);
        if (lastTargetDistance < 10f) {
            target = null;
        }
    }

    private float getDistanceTo(GameObject target) {
        tmp.set(target.getPosition()).sub(player.getShip().getPosition());
        return tmp.len();
    }

    private void moveToTarget(GameObject thisObject, GameObject targetObject) {
        tmp.set(targetObject.getPosition()).sub(thisObject.getPosition());
        lastTargetDistance = tmp.len();
        tmp.nor();
        thisObject.getVelocity().add(tmp.scl(3f));
        mat.set(0f, 0f, 0f, 0f);
        tmp.prj(mat);
        thisObject.getOrientation().setFromMatrix(mat);
    }
}
