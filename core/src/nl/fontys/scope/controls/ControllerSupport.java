package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

abstract class ControllerSupport implements ControllerListener {

    private static final int BUTTON_COUNT = 24;

    private boolean button[];

    protected Moveable moveable;

    private Map<Integer, MoveableAction> actionMapping;

    public ControllerSupport(Moveable moveable) {
        actionMapping = new HashMap<Integer, MoveableAction>();
        button = new boolean[BUTTON_COUNT];
        this.moveable = moveable;
    }

    public final void update(float delta) {
        for (Map.Entry<Integer, MoveableAction> action : actionMapping.entrySet()) {
            int button = action.getKey();
            if (isButtonPressed(button)) {
                action.getValue().act(moveable);
            }
        }
    }

    protected void register(int buttonCode, MoveableAction action) {
        if (validButtonCode(buttonCode)) {
            actionMapping.put(buttonCode, action);
        }
    }

    @Override
    public final void connected(Controller controller) {
        System.out.println("Connected " + controller.getName());
    }

    @Override
    public final void disconnected(Controller controller) {
        System.out.println("Disconnected " + controller.getName());
    }

    @Override
    public final boolean buttonDown(Controller controller, int buttonCode) {
        System.out.println("buttonDown" + buttonCode);
        if (validButtonCode(buttonCode)) {
            button[buttonCode] = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final boolean buttonUp(Controller controller, int buttonCode) {
        if (validButtonCode(buttonCode)) {
            button[buttonCode] = false;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final boolean povMoved(Controller controller, int povCode, PovDirection value) {
        System.out.println("povMoved" + povCode + " -> " + value);
        return false;
    }

    @Override
    public final boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        System.out.println("xSliderMoved" + sliderCode + " -> " + value);
        return false;
    }

    @Override
    public final boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        System.out.println("ySliderMoved" + sliderCode + " -> " + value);
        return false;
    }

    @Override
    public final boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        System.out.println("xSliderMoved" + accelerometerCode + " -> " + value);
        return false;
    }

    private boolean validButtonCode(int code) {
        return code >= 0 && button.length > code;
    }

    private boolean isButtonPressed(int code) {
        return button[code];
    }
}
