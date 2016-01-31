package nl.fontys.scope.util;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

public final class GameObjectUtil {

    private static Matrix4 mat = new Matrix4();

    private static Vector3 v = new Vector3();

    public static void moveToTarget(GameObject thisObject, GameObject targetObject) {
        v.set(targetObject.getPosition()).sub(thisObject.getPosition());
        v.nor();
        thisObject.getVelocity().add(v.scl(1.7f));
        v.set(thisObject.getVelocity());
        v.nor();
        mat.set(v.x, v.y, v.z, v.len());
        thisObject.getOrientation().setFromMatrix(mat);
    }

    public static float distanceTo(GameObject thisObject, GameObject targetObject) {
        v.set(targetObject.getPosition());
        return v.sub(thisObject.getPosition()).len();
    }
}
