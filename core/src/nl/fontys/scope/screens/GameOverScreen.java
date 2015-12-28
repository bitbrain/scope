package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.GameStats;
import nl.fontys.scope.core.controller.CameraRotatingController;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.Styles;

public class GameOverScreen extends AbstractScreen {

    private GameStats stats;

    private boolean touched = false;

    public GameOverScreen(ScopeGame game, GameStats stats) {
        super(game);
        this.stats = stats;
    }

    @Override
    protected void onShow() {
        GameObject planet = factory.createPlanet(0f, 30f, 0f, 0f);
        world.addController(new CameraRotatingController(500f, world.getCamera(), planet));
    }

    @Override
    protected void onUpdate(float delta) {
        if (Gdx.input.isTouched() && !touched) {
            touched = true;
            setScreen(new MenuScreen(game));
        }
    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);
        Label label = new Label("Du hast gewonnen!", Styles.LABEL_CAPTION);
        layout.center().add(label).row();
        stage.addActor(layout);
    }
}
