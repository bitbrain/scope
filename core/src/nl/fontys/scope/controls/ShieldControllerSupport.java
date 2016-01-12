package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controller;

public abstract class ShieldControllerSupport extends ControllerSupport {

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
    protected boolean isSupported(Controller controller) {
        return controller.getName().equals(SHIELD_VERSION);
    }
}
