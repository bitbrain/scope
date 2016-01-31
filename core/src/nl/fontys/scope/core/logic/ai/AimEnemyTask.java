package nl.fontys.scope.core.logic.ai;

import nl.fontys.scope.util.GameObjectUtil;

public class AimEnemyTask extends AITask {

    private static final float MIN_AGGRO_RADIUS = 40f;
    private static final float MAX_AGGRO_RADIUS = 80f;

    @Override
    public Status execute() {
        AIState state = getObject();
        if (state.closestEnemy != null) {
            final float DISTANCE = GameObjectUtil.distanceTo(state.player.getShip(), state.closestEnemy);
            if (DISTANCE > MIN_AGGRO_RADIUS && DISTANCE < MAX_AGGRO_RADIUS) {
                GameObjectUtil.moveToTarget(state.player.getShip(), state.closestEnemy);
                return Status.SUCCEEDED;
            }
        }
        return Status.FAILED;
    }
}
