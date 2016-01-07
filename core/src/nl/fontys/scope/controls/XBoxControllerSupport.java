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
        float boostValue = getAxisValue(Buttons.T_TRIGGER_CODE);
        final float BOOST_TOLERANCE = 0.02f;
        if (boostValue > BOOST_TOLERANCE || boostValue < -BOOST_TOLERANCE) {
            MoveableAction.BOOST.act(moveable, -boostValue);
        }
        float riseValue = getAxisValue(Buttons.RIGHT_STICK_CODE_Y);
        final float RISE_TOLERANCE = 0.02f;
        if (riseValue > RISE_TOLERANCE || riseValue < -RISE_TOLERANCE) {
            MoveableAction.RISE.act(moveable, -riseValue);
        }
    }
}
