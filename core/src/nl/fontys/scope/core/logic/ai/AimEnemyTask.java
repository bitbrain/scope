package nl.fontys.scope.core.logic.ai;

import nl.fontys.scope.util.GameObjectUtil;

public class AimEnemyTask extends AITask {

    private static final float MIN_AGGRO_RADIUS = 70f;
    private static final float MAX_AGGRO_RADIUS = 180f;

    @Override
    public Status execute() {
        AIState state = getObject();
        // Only aim for enemies when player can shoot
        if (!state.player.getWeapon().canShoot()) {
            return Status.FAILED;
        }
        if (state.lastEnemyAttackedBy != null) {
            final float DISTANCE = GameObjectUtil.distanceTo(state.player.getShip(), state.lastEnemyAttackedBy);
            if (DISTANCE > MIN_AGGRO_RADIUS * 2f && DISTANCE < MAX_AGGRO_RADIUS * 2f) {
                GameObjectUtil.moveToTarget(state.player.getShip(), state.lastEnemyAttackedBy, state.delta);
                return Status.SUCCEEDED;
            } else if (DISTANCE < MIN_AGGRO_RADIUS) {
                GameObjectUtil.rotateTo(state.player.getShip(), state.lastEnemyAttackedBy);
                return Status.SUCCEEDED;
            }
        } else if (state.closestEnemy != null) {
            final float DISTANCE = GameObjectUtil.distanceTo(state.player.getShip(), state.closestEnemy);
            if (DISTANCE > MIN_AGGRO_RADIUS && DISTANCE < MAX_AGGRO_RADIUS) {
                GameObjectUtil.moveToTarget(state.player.getShip(), state.closestEnemy, state.delta);
                return Status.SUCCEEDED;
            } else if (DISTANCE < MIN_AGGRO_RADIUS) {
                GameObjectUtil.rotateTo(state.player.getShip(), state.closestEnemy);
                return Status.SUCCEEDED;
            }
        }
        return Status.FAILED;
    }
}
