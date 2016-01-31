package nl.fontys.scope.core.logic.ai;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.util.GameObjectUtil;

public class AimSphereTask extends AITask {

    @Override
    public Status execute() {
        AIState state = getObject();
        Player player = state.player;
        if (player.getFocusProgress() > 0.9f) {
            GameObjectUtil.moveToTarget(player.getShip(), state.sphere, state.delta);
            return Status.SUCCEEDED;
        }
        return Status.FAILED;
    }
}
