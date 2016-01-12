package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.Controllers;

import java.util.ArrayList;
import java.util.List;

public class ControllerManager {

    private KeyboardIngameSupport keyboard;

    private List<ControllerSupport> controllerSupports = new ArrayList<ControllerSupport>();

    public ControllerManager() {
        Controllers.clearListeners();
    }

    public void update(float delta) {
        for (ControllerSupport support : controllerSupports) {
            support.update(delta);
        }
    }

    public void registerSupport(ControllerSupport support) {
        if (!controllerSupports.contains(support)) {
            controllerSupports.add(support);
            Controllers.addListener(support);
        }
    }


}
