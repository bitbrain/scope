package nl.fontys.scope.core.logic.ai;

public class ShootTask extends AITask {

    @Override
    public Status execute() {
        AIState state = getObject();
        if (state.player.getWeapon().canShoot()) {
            state.player.getWeapon().shoot();
            return Status.SUCCEEDED;
        } else {
            return Status.FAILED;
        }
    }
}
