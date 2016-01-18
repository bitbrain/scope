package nl.fontys.scope.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import nl.fontys.scope.Config;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.logic.CameraRotatingLogic;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.Styles;
import nl.fontys.scope.ui.validation.ValidationContext;
import nl.fontys.scope.ui.validation.ValidationTextField;

public class JoinGameScreen extends AbstractScreen implements ExitHandler {

    private ValidationTextField nameText;
    private ButtonMenu menu;

    public JoinGameScreen(ScopeGame game) {
        super(game);
    }

    @Override
    protected void onShow() {
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
        Label caption = new Label(Bundle.general.get(Messages.MENU_JOIN_GAME), Styles.LABEL_CAPTION);
        layout.add(caption).padBottom(55f).row();
        menu = new ButtonMenu(tweenManager);
        Button submit = menu.add(Bundle.general.get(Messages.GAME_JOIN), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setScreen(new JoiningGameScreen(game, nameText.getText()));

            }
        });

        // Validation
        ValidationContext context = new ValidationContext(submit);

        // Name of the game
        Label name = new Label(Bundle.general.get(Messages.GAME_NAME), Styles.LABEL_DESCRIPTION);
        layout.add(name).padBottom(10f).row();
        nameText = new ValidationTextField(Bundle.general.get(Messages.GAME_NAME_HINT), Styles.TEXTFIELD_FORM, context);
        nameText.setFocusTraversal(true);
        nameText.setErrorMessage(Bundle.general.get(Messages.GAME_NAME_INVALID));
        layout.add(nameText).width(Config.MENU_BUTTON_WIDTH).height(85f).row();

        layout.add(context.getLabel()).padTop(10f).row();
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
