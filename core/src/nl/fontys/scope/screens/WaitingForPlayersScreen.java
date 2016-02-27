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

public class WaitingForPlayersScreen extends AbstractScreen implements ExitHandler, GameClient.GameClientListener {

    private IngameScreen ingameScreen;

    private String gameName;

    private Events events = Events.getInstance();

    private GameClient client;

    private Label caption;

    public WaitingForPlayersScreen(ScopeGame game, String gameName) {
        super(game);
        this.gameName = gameName;
    }

    @Override
    protected void onShow() {
        World world = new World();
        ingameScreen = new IngameScreen(game, world, false);
        client = new GameClient(events, gameName, world, ingameScreen.getPlayerManager());
        client.addListener(this);
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


        layout.add(caption);
        System.out.println(caption + "...");
        stage.addActor(layout);
    }

    @Override
    public void exit() {
        setScreen(new MenuScreen(game));
    }

    @Override
    public void onGameCreated(Responses.GameCreated created) {
        if (caption == null) {
            caption = new Label("Waiting for players", Styles.LABEL_CAPTION);
        }
        caption.setText("Waiting for players (" + created.getCurrentClients() + "/" + created.getMaxClients() + ")");
    }

    @Override
    public void onClientJoined(Responses.ClientJoined joined) {
    }

    @Override
    public void onClientLeft(Responses.ClientLeft left) {

    }

    @Override
    public void onGameReady(Responses.GameReady ready) {

    }

    @Override
    public void onGameAborted() {

    }
}
