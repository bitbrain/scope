package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.Stage;

import net.engio.mbassy.listener.Handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.controls.ControllerControls;
import nl.fontys.scope.controls.KeyboardControls;
import nl.fontys.scope.core.Arena;
import nl.fontys.scope.core.GameLogicHandler;
import nl.fontys.scope.core.GameStats;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.controller.CameraTrackingController;
import nl.fontys.scope.core.controller.ShipController;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.ui.DebugWidget;
import nl.fontys.scope.ui.GameProgressWidget;
import nl.fontys.scope.ui.PlayerInfoWidget;
import nl.fontys.scope.ui.FocusBarWidget;
import nl.fontys.scope.ui.TooltipController;

public class IngameScreen extends AbstractScreen {

    private KeyboardControls keyboardControls;

    private CameraTrackingController camController;

    private List<ControllerControls> controls = new ArrayList<ControllerControls>();

    private boolean debug;

    private DebugWidget debugWidget;

    private Arena arena;

    private GameLogicHandler logicHandler;

    private TooltipController tooltipController;

    private PlayerManager playerManager;

    // Todo: synchronize stats over the network
    private GameStats stats = new GameStats();

    public IngameScreen(ScopeGame game, boolean debug) {
        super(game);
        this.debug = debug;
    }

    @Override
    protected void onShow() {
        events.register(this);
        playerManager = new PlayerManager(world);
        ShipController controller = new ShipController();
        world.addController(PlayerManager.getCurrent().getShip(), controller);
        camController = new CameraTrackingController(world.getCamera());
        world.addController(PlayerManager.getCurrent().getShip(), camController);
        arena = new Arena(factory, 2);
        arena.setup(playerManager, tweenManager);
        world.setRestrictor(arena.getRestrictor());
        logicHandler = new GameLogicHandler(world, factory, arena, playerManager);
        tooltipController = new TooltipController(playerManager);
        keyboardControls = new KeyboardControls(controller);
        ControllerControls cc = new ControllerControls(controller);
        controls.add(cc);
        Controllers.addListener(cc);
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
        PlayerInfoWidget widget = new PlayerInfoWidget(world.getCamera(), playerManager, tweenManager);
        stage.addActor(widget);
        final float LIFE_WIDGET_WIDTH = 350f;
        FocusBarWidget lifeWidget = new FocusBarWidget(PlayerManager.getCurrent(), tweenManager);
        lifeWidget.setSize(LIFE_WIDGET_WIDTH, 40f);
        lifeWidget.setPosition(Gdx.graphics.getWidth() / 2f - lifeWidget.getWidth() / 2f, Gdx.graphics.getHeight() - 50f - lifeWidget.getHeight());
        stage.addActor(lifeWidget);

        Iterator<Player> playerIterator = playerManager.getPlayers().iterator();
        if (playerIterator.hasNext()) {
            GameProgressWidget progress = new GameProgressWidget(playerIterator.next(), tweenManager);
            progress.setPosition(Gdx.graphics.getWidth() - progress.getWidth() - 50f, Gdx.graphics.getHeight() - progress.getHeight() - 50f);
            stage.addActor(progress);
        }
        if (playerIterator.hasNext()) {
            GameProgressWidget progress = new GameProgressWidget(playerIterator.next(), tweenManager);
            progress.setPosition(50f, Gdx.graphics.getHeight() - progress.getHeight() - 50f);
            stage.addActor(progress);
        }

    }

    @Handler
    public void onEvent(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.GAME_OVER)) {
            stats.winner = (Player) event.getPrimaryParam();
            setScreen(new GameOverScreen(game, stats));
        }
    }
}
