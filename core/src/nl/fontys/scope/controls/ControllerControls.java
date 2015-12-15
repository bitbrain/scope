package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;

/**
 * Created by Miguel on 11/21/2015.
 */
public class ControllerControls implements ControllerListener {

    private static final int BUTTON_A = 0;
    private static final int BUTTON_B = 1;
    private static final int BUTTON_X = 2;

    private Moveable moveable;

    private boolean[] pressed = new boolean[12];

    public ControllerControls(Moveable moveable) {
        this.moveable = moveable;
    }

    public void update(float delta) {
        if (isDown(BUTTON_A)) {
            moveable.moveForward();
        }
        if (isDown(BUTTON_B)) {
            moveable.moveUp();
        } else if (isDown(BUTTON_X)) {
            moveable.moveDown();
        }
        if (isDown(6)) {
            moveable.moveRight();
        } else if (isDown(7)) {
            moveable.moveLeft();
        }
    }

    @Override
    public void connected(Controller controller) {
        System.out.println("Connected " + controller.getName());
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println("Disconnected " + controller.getName());
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        pressed[buttonCode] = true;
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        pressed[buttonCode] = false;
        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return true;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        if (value.equals(PovDirection.east)) {
            moveable.moveRight();
            pressed[6] = true;
        } else if  (value.equals(PovDirection.west)) {
            pressed[7] = true;
        } else {
            pressed[6] = false;
            pressed[7] = false;
        }
        return true;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return true;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return true;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return true;
    }

    private boolean isDown(int button) {
        return pressed[button];
    }
}
