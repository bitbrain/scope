package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import nl.fontys.scope.Config;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.controller.CameraRotatingController;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.networking.ScopeClient;
import nl.fontys.scope.networking.ScopeServer;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.Styles;
import nl.fontys.scope.ui.validation.ValidationContext;
import nl.fontys.scope.ui.validation.ValidationTextField;

public class CreateGameScreen extends AbstractScreen {

    public CreateGameScreen(ScopeGame game) {
        super(game);
    }

    @Override
    protected void onShow() {
        GameObject planet = factory.createPlanet(80f);
        world.addController(new CameraRotatingController(500f, world.getCamera(), planet));
    }

    @Override
    protected void onUpdate(float delta) {
        // Input handling
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            setScreen(new MenuScreen(game));
        }
    }

    @Override
    protected void onCreateStage(Stage stage) {
        final Table layout = new Table();
        layout.setFillParent(true);
        Label caption = new Label(Bundle.general.get(Messages.MENU_NEW_GAME), Styles.LABEL_CAPTION);
        layout.add(caption).padBottom(55f).row();

        ButtonMenu menu = new ButtonMenu(tweenManager);
        Button submit = menu.add(Bundle.general.get(Messages.GAME_CREATE), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                IngameScreen screen = new IngameScreen(game, false);
                screen.show();

                game.startServer();

                game.setClient(new ScopeClient(screen.world));
                game.getClient().connectToServer(game.getClient().findServer(), 54555, 54777);
                game.getClient().createGame(2, "Test");
                long gameID = game.getClient().searchGame("Test");
                game.getClient().joinGame(gameID);

                setScreen(new WaitingForPlayersScreen(game));
            }
        });

        // Validation
        ValidationContext context = new ValidationContext(submit);

        // Name of the game
        Label name = new Label(Bundle.general.get(Messages.GAME_NAME), Styles.LABEL_DESCRIPTION);
        layout.add(name).padBottom(10f).row();
        ValidationTextField nameText = new ValidationTextField(Bundle.general.get(Messages.GAME_NAME_HINT), Styles.TEXTFIELD_FORM, context);
        nameText.setFocusTraversal(true);
        nameText.setErrorMessage(Bundle.general.get(Messages.GAME_NAME_INVALID));
        layout.add(nameText).width(Config.MENU_BUTTON_WIDTH).height(85f).row();

        layout.add(context.getLabel()).padTop(10f).row();
        layout.add(menu).padTop(30f).row();
        stage.addActor(layout);
    }
}
