package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Xbox;

class ShieldControllerSupport extends ControllerSupport {

    public static class Buttons {
        public static final int A = 0;
        public static final int B = 0;
        public static final int X = 0;
        public static final int Y = 0;
        public static final int LB = 0;
        public static final int RB = 0;
        public static final int BACK = 0;
        public static final int START = 0;
        public static final int T_TRIGGER_CODE = 4;
        public static final int LEFT_STICK_CODE_Y = 0;
        public static final int LEFT_STICK_CODE_X = 0;
        public static final int RIGHT_STICK_CODE_Y = 0;
        public static final int RIGHT_STICK_CODE_X = 0;
    }

    public ShieldControllerSupport(Moveable moveable) {
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

    @Override
    protected boolean isSupported(Controller controller) {
        return controller.getName().equals("Shield");
    }

    private void act(MoveableAction action, int code, float tolerance) {
        float value = getAxisValue(code);
        if (value > tolerance || value < -tolerance) {
            action.act(moveable, -value);
        }
    }
}
