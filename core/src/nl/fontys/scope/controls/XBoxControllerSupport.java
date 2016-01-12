package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Xbox;

public abstract class XBoxControllerSupport extends ControllerSupport {

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
    }

    @Override
    protected final boolean isSupported(Controller controller) {
        return Xbox.isXboxController(controller);
    }
}
