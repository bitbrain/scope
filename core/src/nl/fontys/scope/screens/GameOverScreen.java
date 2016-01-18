package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.GameStats;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.logic.CameraRotatingLogic;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.Styles;

public class GameOverScreen extends AbstractScreen implements ExitHandler {

    private GameStats stats;

    private boolean touched = false;

    public GameOverScreen(ScopeGame game, GameStats stats) {
        super(game);
        this.stats = stats;
    }

    @Override
    protected void onShow() {
        GameObject planet = factory.createPlanet(30f);
        world.addLogic(new CameraRotatingLogic(500f, world.getCamera(), planet));
    }

    @Override
    protected void onUpdate(float delta) {
        if (Gdx.input.isTouched() && !touched) {
            touched = true;
            exit();
        }
    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);
        Label label = new Label(getCaptionLabelText(), Styles.LABEL_CAPTION);
        layout.center().add(label).row();
        stage.addActor(layout);
    }

    private String getCaptionLabelText() {
        if (PlayerManager.getCurrent().equals(stats.winner)) {
            return Bundle.general.get(Messages.WIN);
        } else {
            return Bundle.general.get(Messages.LOSS);
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
