package nl.fontys.scope.core.logic.ai;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public class AITask extends LeafTask<AIState> {

    @Override
    public Status execute() {
        return Status.SUCCEEDED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
