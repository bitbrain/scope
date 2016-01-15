package nl.fontys.scope.controls;

public class ShieldIngameControllerSupport extends ShieldControllerSupport {

    public ShieldIngameControllerSupport(Moveable moveable) {
        super(moveable);
    }

    @Override
    protected void onUpdate() {
        final float TOLERANCE = 0.02f;
        act(MoveableAction.SHOOT, Buttons.T_TRIGGER_CODE_L, TOLERANCE, true);
        act(MoveableAction.BOOST, Buttons.T_TRIGGER_CODE_R, TOLERANCE, false);
        act(MoveableAction.RISE, Buttons.RIGHT_STICK_CODE_Y, TOLERANCE, true);

        final float ROTATION_SPEED = 1.5f;
        float value = getAxisValue(Buttons.LEFT_STICK_CODE_X);
        if (value > 0.2f || value < -0.2f) {
            MoveableAction.ROTATE.act(moveable, value * ROTATION_SPEED, 0f, 0f);
        }
        value = getAxisValue(Buttons.LEFT_STICK_CODE_Y);
        if (value > 0.2f || value < -0.2f) {
            MoveableAction.ROTATE.act(moveable, 0f, 0f, value * ROTATION_SPEED);
        }
        value = getAxisValue(Buttons.RIGHT_STICK_CODE_X);
        if (value > 0.2f || value < -0.2f) {
            MoveableAction.ROTATE.act(moveable, 0f, -value * ROTATION_SPEED, 0f);
        }
    }

    private void act(MoveableAction action, int code, float tolerance, boolean inverse) {
        float value = getAxisValue(code);
        if (value > tolerance || value < -tolerance) {
            action.act(moveable, inverse ? -value : value);
        }
    }
}
