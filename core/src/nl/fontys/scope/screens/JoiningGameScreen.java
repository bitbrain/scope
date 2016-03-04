package nl.fontys.scope.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.logic.CameraRotatingLogic;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.Styles;

public class JoiningGameScreen extends AbstractScreen implements ExitHandler {

    private IngameScreen ingameScreen;

    private Events events = Events.getInstance();

    private String gameName;

    private GameClient client;

    private GameClient.GameClientHandler handler;

    public JoiningGameScreen(ScopeGame game, String name) {
        super(game);
        this.gameName = name;
    }

    @Override
    protected void onShow() {
        World world = new World();
        this.ingameScreen = new IngameScreen(game, world, false) {
            @Override
            protected void onShow() {
                super.onShow();
                // Register again the client here
                client.setEvents(Events.getInstance());
            }
        };
        events.register(this);
        GameObject planet = factory.createPlanet(30f);
        world.addLogic(new CameraRotatingLogic(800f, world.getCamera(), planet));
        client = new GameClient(events, gameName, world, ingameScreen.getPlayerManager());
        handler = new GameClient.GameClientHandler() {
            @Override
            public void onGameClosed(Responses.GameClosed closed) {
                System.out.println("Game closed..");
                exit();
            }

            @Override
            public void onGameReady(Responses.GameReady ready) {
                setScreen(ingameScreen);
            }

            @Override
            public void onClientLeft(Responses.ClientLeft left) {
                System.out.println("Client left..");
            }

            @Override
            public void onClientJoined(Responses.ClientJoined joined) {
                System.out.println("Client joined");
            }

            @Override
            public void onConnectionFailed() {
                exit();
            }
        };
        client.connect(false);
    }

    @Override
    protected void onUpdate(float delta) {

    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);
        Label caption = new Label("Joining game '" + gameName + "'", Styles.LABEL_CAPTION);
        World world = new World();
        ingameScreen = new IngameScreen(game, world, false);
        layout.add(caption);
        stage.addActor(layout);
    }

    @Override
    public void exit() {
        setScreen(new MenuScreen(game));
    }

    @Override
    protected ExitHandler getExitHandler() {
        return this;
    }

    @Override
    protected void onDispose() {
        client.leaveCurrentGame();
        client.removeHandler(handler);
    }
}