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

public class WaitingForPlayersScreen extends AbstractScreen implements ExitHandler {

    private IngameScreen ingameScreen;

    private String gameName;

    private Events events = Events.getInstance();

    public WaitingForPlayersScreen(ScopeGame game, String gameName) {
        super(game);
        this.gameName = gameName;
    }

    @Override
    protected void onShow() {

        World world = new World();
        ingameScreen = new IngameScreen(game, world, false);
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

        Label caption = new Label("Waiting for Players", Styles.LABEL_CAPTION);

        layout.add(caption);
        stage.addActor(layout);
    }

    @Override
    public void exit() {
        setScreen(new MenuScreen(game));
    }
}
