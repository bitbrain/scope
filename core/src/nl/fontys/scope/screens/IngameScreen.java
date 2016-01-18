package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import net.engio.mbassy.listener.Handler;

import java.util.Iterator;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.controls.KeyboardIngameSupport;
import nl.fontys.scope.controls.ShieldIngameControllerSupport;
import nl.fontys.scope.controls.XBoxIngameControllerSupport;
import nl.fontys.scope.core.Arena;
import nl.fontys.scope.core.GameLogicHandler;
import nl.fontys.scope.core.GameStats;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.logic.CameraTrackingLogic;
import nl.fontys.scope.core.logic.ShipLogic;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.ui.DebugWidget;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.FocusBarWidget;
import nl.fontys.scope.ui.GameProgressWidget;
import nl.fontys.scope.ui.PlayerInfoWidget;
import nl.fontys.scope.ui.TooltipController;

public class IngameScreen extends AbstractScreen implements ExitHandler {

    public interface IngameInitializer {
        void initialize(IngameScreen screen);
    }

    private CameraTrackingLogic camController;

    private KeyboardIngameSupport keyboard;

    private boolean debug;

    private DebugWidget debugWidget;

    private Arena arena;

    private GameLogicHandler logicHandler;

    private TooltipController tooltipController;

    private PlayerManager playerManager;

    // Todo: synchronize stats over the network
    private GameStats stats = new GameStats();

    private IngameInitializer initializer;

    public IngameScreen(ScopeGame game, World world, boolean debug) {
        super(game, world);
        this.debug = debug;
    }

    public IngameScreen(ScopeGame game, World world, IngameInitializer initializer) {
        this(game, world, false);
        this.initializer = initializer;
    }

    @Override
    protected void onShow() {
        events.register(this);
        playerManager = new PlayerManager(world);
        ShipLogic controller = new ShipLogic();
        world.addLogic(PlayerManager.getCurrent().getShip(), controller);
        camController = new CameraTrackingLogic(world.getCamera());
        world.addLogic(PlayerManager.getCurrent().getShip(), camController);
        arena = new Arena(factory, 2);
        world.setRestrictor(arena.getRestrictor());
        logicHandler = new GameLogicHandler(world, factory, arena, playerManager);
        tooltipController = new TooltipController(playerManager);
        controllerManager.registerSupport(new XBoxIngameControllerSupport(controller));
        controllerManager.registerSupport(new ShieldIngameControllerSupport(controller));
        keyboard = new KeyboardIngameSupport(controller);
        if (initializer != null) {
            initializer.initialize(this);
        }
        arena.setup(playerManager, world, tweenManager);
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    protected void onUpdate(float delta) {
        keyboard.update(delta);
    }

    @Override
    protected void onCreateStage(Stage stage) {
        debugWidget = new DebugWidget();
        stage.addActor(debugWidget);
        debugWidget.setVisible(debug);
        PlayerInfoWidget widget = new PlayerInfoWidget(world.getCamera(), playerManager, tweenManager);
        stage.addActor(widget);
        final float LIFE_WIDGET_WIDTH = Gdx.graphics.getWidth() / 3f;
        FocusBarWidget lifeWidget = new FocusBarWidget(PlayerManager.getCurrent(), tweenManager);
        lifeWidget.setSize(LIFE_WIDGET_WIDTH, LIFE_WIDGET_WIDTH / 8f);
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

    @Override
    protected ExitHandler getExitHandler() {
        return this;
    }

    @Override
    public void exit() {
        setScreen(new MenuScreen(game));
    }
}
