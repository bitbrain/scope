package nl.fontys.scope.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.logic.CameraRotatingLogic;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.Styles;

public class WaitingForPlayersScreen extends AbstractScreen implements ExitHandler {

    private IngameScreen ingameScreen;

    private String gameName;

    private Events events = Events.getInstance();

    private GameClient client;

    private Label caption;

    private GameClient.GameClientHandler handler;

    public WaitingForPlayersScreen(ScopeGame game, String gameName) {
        super(game);
        this.gameName = gameName;
    }

    @Override
    protected void onShow() {
        World world = new World();
        ingameScreen = new IngameScreen(game, world, false) {
            @Override
            protected void onShow() {
                super.onShow();
                // Register again the client here
                client.setEvents(Events.getInstance());
            }
        };
        client = new GameClient(events, gameName, world, ingameScreen.getPlayerManager());
        handler = new GameClient.GameClientHandler() {
            @Override
            public void onClientJoined(Responses.ClientJoined joined) {
                if (caption == null) {
                    caption = new Label("Waiting for players", Styles.LABEL_CAPTION);
                }
                caption.setText("Waiting for players (" + joined.getCurrentClients() + "/" + joined.getMaxClients() + ")");
            }

            @Override
            public void onClientLeft(Responses.ClientLeft left) {
                if (caption == null) {
                    caption = new Label("Waiting for players", Styles.LABEL_CAPTION);
                }
                caption.setText("Waiting for players (" + left.getCurrentClients() + "/" + left.getMaxClients() + ")");
            }

            @Override
            public void onGameClosed(Responses.GameClosed closed) {
                WaitingForPlayersScreen.this.exit();
            }

            @Override
            public void onGameCreated(Responses.GameCreated created) {
                if (caption == null) {
                    caption = new Label("Waiting for players", Styles.LABEL_CAPTION);
                }
                caption.setText("Waiting for players (" + created.getCurrentClients() + "/" + created.getMaxClients() + ")");
            }

            @Override
            public void onGameReady(Responses.GameReady ready) {
                setScreen(ingameScreen);
            }

            @Override
            public void onConnectionFailed() {
                WaitingForPlayersScreen.this.exit();
            }
        };
        client.addHandler(handler);
        events.register(this);
        GameObject planet = factory.createPlanet(30f);
        world.addLogic(new CameraRotatingLogic(800f, world.getCamera(), planet));
        client.connect(true);
    }

    @Override
    protected void onUpdate(float delta) {
    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);

        layout.add(caption).row();
        ButtonMenu menu = new ButtonMenu(tweenManager);
        menu.add("Abort", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                client.leaveCurrentGame();
                setScreen(new MenuScreen(game));
            }
        });
        layout.add(menu).padTop(60);
        stage.addActor(layout);
    }

    @Override
    public void exit() {
        setScreen(new MenuScreen(game));
    }

    @Override
    protected void onDispose() {
        client.leaveCurrentGame();
        client.removeHandler(handler);
    }
}
