package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controller;

class XBoxControllerSupport extends ControllerSupport {

    public static class Buttons {
        public static final int A = 0;
        public static final int B = 1;
        public static final int X = 2;
        public static final int Y = 3;
        public static final int LB = 4;
        public static final int RB = 5;
        public static final int BACK = 6;
        public static final int START = 7;
        public static final int LEFT_STICK = 8;
        public static final int RIGHT_STICK = 9;
        public static final int T_TRIGGER_CODE = 4;
        public static final int LEFT_STICK_CODE_Y = 0;
        public static final int LEFT_STICK_CODE_X = 1;
        public static final int RIGHT_STICK_CODE_Y = 2;
        public static final int RIGHT_STICK_CODE_X = 3;
    }

    public XBoxControllerSupport(Moveable moveable) {
        super(moveable);
        register(Buttons.A, MoveableAction.SHOOT);
    }

    @Override
    protected void onUpdate() {
        act(MoveableAction.BOOST, Buttons.T_TRIGGER_CODE, 0.02f);
        act(MoveableAction.RISE, Buttons.RIGHT_STICK_CODE_Y, 0.02f);
    }

    private void act(MoveableAction action, int code, float tolerance) {
        float value = getAxisValue(code);
        act(action, code, tolerance, -value);
    }

    private void act(MoveableAction action, int code, float tolerance, Object ... args) {
        float value = getAxisValue(code);
        if (value > tolerance || value < -tolerance) {
            action.act(moveable, args);
        }
    }
}
