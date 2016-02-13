package nl.fontys.scope.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.logic.CameraRotatingLogic;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.Styles;

public class JoiningGameScreen extends AbstractScreen implements ExitHandler {

    private IngameScreen ingameScreen;

    private Thread keepAliveThread;

    private Events events = Events.getInstance();

    private String gameName;

    public JoiningGameScreen(ScopeGame game, String name) {
        super(game);
        this.gameName = name;
    }

    @Override
    protected void onShow() {
        World world = new World();
        this.ingameScreen = new IngameScreen(game, world, false);
        events.register(this);
        GameObject planet = factory.createPlanet(30f);
        world.addLogic(new CameraRotatingLogic(800f, world.getCamera(), planet));
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
}