package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Xbox;

class ShieldControllerSupport extends ControllerSupport {

    private static final String SHIELD_VERSION = "NVIDIA Corporation NVIDIA Controller v01.03";

    public static class Buttons {
        public static final int A = 96;
        public static final int B = 97;
        public static final int X = 99;
        public static final int Y = 100;
        public static final int LB = 102;
        public static final int RB = 103;
        public static final int BACK = 4;
        public static final int START = 108;
        public static final int T_TRIGGER_CODE_L = 6;
        public static final int T_TRIGGER_CODE_R = 5;
        public static final int LEFT_STICK_CODE_Y = 1;
        public static final int LEFT_STICK_CODE_X = 0;
        public static final int RIGHT_STICK_CODE_Y = 3;
        public static final int RIGHT_STICK_CODE_X = 2;
    }

    public ShieldControllerSupport(Moveable moveable) {
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

    @Override
    protected boolean isSupported(Controller controller) {
        return controller.getName().equals(SHIELD_VERSION);
    }

    private void act(MoveableAction action, int code, float tolerance, boolean inverse) {
        float value = getAxisValue(code);
        if (value > tolerance || value < -tolerance) {
            action.act(moveable, inverse ? -value : value);
        }
    }
}
