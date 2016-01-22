package nl.fontys.scope.core.logic.ai;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public class AimEnergyTask extends LeafTask {

    @Override
    public Status execute() {
        return Status.FAILED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
