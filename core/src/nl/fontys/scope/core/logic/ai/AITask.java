package nl.fontys.scope.core.logic.ai;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

public class AITask extends LeafTask<AIState> {

    private Matrix4 mat = new Matrix4();

    private Vector3 v = new Vector3();

    @Override
    public Status execute() {
        return Status.SUCCEEDED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }

    protected void moveToTarget(GameObject thisObject, GameObject targetObject) {
        v.set(targetObject.getPosition()).sub(thisObject.getPosition());
        v.nor();
        thisObject.getVelocity().add(v.scl(1.7f));
        v.set(thisObject.getVelocity());
        v.nor();
        mat.set(v.x, v.y, v.z, v.len());
        thisObject.getOrientation().setFromMatrix(mat);
    }
}
