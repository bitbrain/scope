package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import nl.fontys.scope.core.GameLogicHandler;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.controller.CameraTrackingController;
import nl.fontys.scope.core.controller.ShipController;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.DebugWidget;
import nl.fontys.scope.ui.FocusWidget;
import nl.fontys.scope.ui.LifeWidget;

public class IngameScreen extends AbstractScreen {

    private KeyboardControls keyboardControls;

    private CameraTrackingController camController;

    private List<ControllerControls> controls = new ArrayList<ControllerControls>();

    private boolean debug;

    private DebugWidget debugWidget;

    private Arena arena;

    private GameLogicHandler logicHandler;

    private PlayerManager playerManager;

    public IngameScreen(ScopeGame game, boolean debug) {
        super(game);
        this.debug = debug;
    }

    @Override
    protected void onShow() {
        playerManager = new PlayerManager(world);
        ShipController controller = new ShipController();
        world.addController(PlayerManager.getCurrent().getShip(), controller);
        camController = new CameraTrackingController(world.getCamera());
        world.addController(PlayerManager.getCurrent().getShip(), camController);
        arena = new Arena(factory, 2);
        arena.setup(playerManager);
        world.setRestrictor(arena.getRestrictor());
        logicHandler = new GameLogicHandler(world, factory, arena, playerManager);

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

        // Input handling
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            setScreen(new MenuScreen(game));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            PlayerManager.getCurrent().addDamage(0.1f);
        }
    }

    @Override
    protected void onCreateStage(Stage stage) {
        debugWidget = new DebugWidget();
        stage.addActor(debugWidget);
        debugWidget.setVisible(debug);
        FocusWidget widget = new FocusWidget(world.getCamera(), playerManager, tweenManager);
        stage.addActor(widget);
        final float LIFE_WIDGET_WIDTH = 350f;
        LifeWidget lifeWidget = new LifeWidget(PlayerManager.getCurrent(), tweenManager);
        lifeWidget.setSize(LIFE_WIDGET_WIDTH, 40f);
        lifeWidget.setPosition(Gdx.graphics.getWidth() / 2f - lifeWidget.getWidth() / 2f, Gdx.graphics.getHeight() - 50f - lifeWidget.getHeight());

        stage.addActor(lifeWidget);
    }
}
