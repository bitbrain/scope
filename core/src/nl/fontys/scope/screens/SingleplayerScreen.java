package nl.fontys.scope.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import nl.fontys.scope.Config;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.AIController;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.Styles;
import nl.fontys.scope.ui.validation.ValidationContext;
import nl.fontys.scope.ui.validation.ValidationTextField;

public class SingleplayerScreen extends AbstractScreen implements ExitHandler {

    private ButtonMenu menu;

    public SingleplayerScreen(ScopeGame game) {
        super(game);
    }

    @Override
    protected void onShow() {

    }

    @Override
    protected void onUpdate(float delta) {

    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);
        Label caption = new Label(Bundle.general.get(Messages.MENU_SINGLE_PLAYER), Styles.LABEL_CAPTION);
        layout.add(caption).padBottom(55f).row();
        menu = new ButtonMenu(tweenManager);
        menu.add(Bundle.general.get(Messages.GAME_JOIN), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                IngameScreen screen = new IngameScreen(game, new World(), new IngameScreen.IngameInitializer() {
                    @Override
                    public void initialize(IngameScreen screen) {
                        PlayerManager playerManager = screen.getPlayerManager();
                        Player player = playerManager.addPlayer();
                        player.setAI(true);
                        screen.world.addController(player.getShip(), new AIController(player));
                    }
                });
                setScreen(screen);

            }
        });
        layout.add(menu).padTop(30f).row();
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
    public ButtonMenu getMenu() {
        return menu;
    }
}
