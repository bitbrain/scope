package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.controls.ControllerControls;
import nl.fontys.scope.controls.KeyboardControls;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.controller.CameraController;
import nl.fontys.scope.core.controller.ShipController;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.DebugWidget;

public class IngameScreen extends AbstractScreen {

    private KeyboardControls keyboardControls;

    private CameraController camController;

    private List<ControllerControls> controls = new ArrayList<ControllerControls>();

    private boolean debug;

    private DebugWidget debugWidget;

    public IngameScreen(ScopeGame game, boolean debug) {
        super(game);
        this.debug = debug;
    }

    @Override
    protected void onShow() {
        soundManager.play(Assets.Musics.STARSURFER, true);

        GameObject ship = factory.createShip(0f, 0f, 0f);
        ShipController controller = new ShipController();
        world.addController(ship, controller);
        camController = new CameraController(world.getCamera());
        world.addController(ship, camController);
        Player.setCurrent(ship);

        keyboardControls = new KeyboardControls(controller);

        SecureRandom random = new SecureRandom(UUID.randomUUID().toString().getBytes());
        factory.createEnergy(random.nextFloat() * 50f - 50f, random.nextFloat() * 50f - 50f, random.nextFloat() * 50f - 50f);
        factory.createPlanet(500f, 50f, 34f, 1.6f);
        factory.createPlanet(1000f, 80f, 234f, 1.2f);
        factory.createPlanet(1500f, 20f, 44f, 2.7f);
        factory.createPlanet(700f, 10f, 44f, 4.7f);
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
