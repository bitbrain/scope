package nl.fontys.scope.util;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

public final class GameObjectUtil {

    private static Matrix4 mat = new Matrix4();

    private static Vector3 v = new Vector3();

    public static void moveToTarget(GameObject thisObject, GameObject targetObject, float delta) {
        if (thisObject != null && targetObject != null) {
            v.set(targetObject.getPosition()).sub(thisObject.getPosition());
            v.nor();
            thisObject.getVelocity().add(v.scl(95f * delta));
            rotateTo(thisObject, targetObject);
        }
    }

    public static void rotateTo(GameObject thisObject, GameObject targetObject) {
        if (thisObject != null && targetObject != null) {
            v.set(targetObject.getPosition());
            v.set(v.sub(thisObject.getPosition()));
            v.nor();
            mat.set(v.x, v.y, v.z, v.len());
            thisObject.getOrientation().setFromMatrix(mat);
        }
    }

    public static float distanceTo(GameObject thisObject, GameObject targetObject) {
        if (thisObject != null && targetObject != null) {
            v.set(targetObject.getPosition());
            return v.sub(thisObject.getPosition()).len();
        } else {
            return 0f;
        }
    }
}
