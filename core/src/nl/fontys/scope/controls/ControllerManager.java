package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controllers;

import java.util.ArrayList;
import java.util.List;

public class ControllerManager {

    private Moveable moveable;

    private KeyboardSupport keyboard;

    private List<ControllerSupport> controllerSupports = new ArrayList<ControllerSupport>();

    public ControllerManager(Moveable moveable) {
        this.moveable = moveable;
        keyboard = new KeyboardSupport(moveable);
        registerSupport(new XBoxControllerSupport(moveable));
        registerSupport(new ShieldControllerSupport(moveable));
    }

    public void update(float delta) {
        keyboard.update(delta);
        for (ControllerSupport support : controllerSupports) {
            support.update(delta);
        }
    }

    private void registerSupport(ControllerSupport support) {
        if (!controllerSupports.contains(support)) {
            controllerSupports.add(support);
            Controllers.addListener(support);
        }
    }


}
