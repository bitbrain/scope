package nl.fontys.scope.screens;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.controls.ControllerControls;
import nl.fontys.scope.controls.KeyboardControls;
import nl.fontys.scope.core.Arena;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.controller.CameraTrackingController;
import nl.fontys.scope.core.controller.ShipController;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.DebugWidget;

public class IngameScreen extends AbstractScreen {

    private KeyboardControls keyboardControls;

    private CameraTrackingController camController;

    private List<ControllerControls> controls = new ArrayList<ControllerControls>();

    private boolean debug;

    private DebugWidget debugWidget;

    private Arena arena;

    public IngameScreen(ScopeGame game, boolean debug) {
        super(game);
        this.debug = debug;
    }

    @Override
    protected void onShow() {
        soundManager.play(Assets.Musics.STARSURFER, true);

        GameObject ship = factory.createShip(0f, 0f, 0f);
        ship.getColor().set(0.65f, 0.65f, 0.65f, 1f);
        ShipController controller = new ShipController();
        world.addController(ship, controller);
        camController = new CameraTrackingController(world.getCamera());
        world.addController(ship, camController);
        arena = new Arena(factory);
        arena.setup();
        Player.setCurrent(ship);

        keyboardControls = new KeyboardControls(controller);
        for (Controller c : Controllers.getControllers()) {
            ControllerControls cc = new ControllerControls(controller);
            controls.add(cc);
            c.addListener(cc);
        }
    }

    @Override
    protected void onUpdate(float delta) {
        keyboardControls.update(delta);
        for (ControllerControls c : controls) {
            c.update(delta);
        }
    }

    @Override
    protected void onCreateStage(Stage stage) {
        debugWidget = new DebugWidget();
        stage.addActor(debugWidget);
        debugWidget.setVisible(debug);
    }
}
