package nl.fontys.scope.core.logic.ai;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.util.GameObjectUtil;

public class AimEnergyTask extends AITask {

    @Override
    public Status execute() {
        AIState state = getObject();
        Player player = state.player;
        if (state.closestEnergy != null) {
            GameObjectUtil.moveToTarget(player.getShip(), state.closestEnergy, state.delta);
            return Status.SUCCEEDED;
        }
        return Status.FAILED;
    }
}
