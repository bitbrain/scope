package nl.fontys.scope.controls;

public class XBoxIngameControllerSupport extends XBoxControllerSupport {

    public XBoxIngameControllerSupport(Moveable moveable) {
        super(moveable);
    }

    @Override
    protected void onUpdate() {
        float value = getAxisValue(Buttons.T_TRIGGER_CODE);
        final float TOLERANCE = 0.02f;
        if (value > TOLERANCE) {
            MoveableAction.SHOOT.act(moveable);
        } else {
            act(MoveableAction.BOOST, Buttons.T_TRIGGER_CODE, TOLERANCE);
        }
        act(MoveableAction.RISE, Buttons.RIGHT_STICK_CODE_Y, TOLERANCE);

        final float ROTATION_SPEED = 1.5f;
        value = getAxisValue(Buttons.LEFT_STICK_CODE_X);
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

    private void act(MoveableAction action, int code, float tolerance) {
        float value = getAxisValue(code);
        if (value > tolerance || value < -tolerance) {
            action.act(moveable, -value);
        }
    }
}
