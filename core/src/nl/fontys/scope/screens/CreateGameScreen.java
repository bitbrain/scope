package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.controller.CameraRotatingController;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.Styles;

public class CreateGameScreen extends AbstractScreen {

    public CreateGameScreen(ScopeGame game) {
        super(game);
    }

    @Override
    protected void onShow() {
        GameObject planet = factory.createPlanet(0f, 80f, 0f, 0f);
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
        Table layout = new Table();
        layout.setFillParent(true);
        Label caption = new Label(Bundle.general.get(Messages.MENU_NEW_GAME), Styles.LABEL_CAPTION);
        layout.add(caption).padBottom(55f).row();

        // Name of the game
        Label name = new Label("Name", Styles.LABEL_DESCRIPTION);
        layout.add(name).padBottom(10f).row();
        TextField nameText = new TextField("Enter name...", Styles.TEXTFIELD_FORM);
        layout.add(nameText).width(ButtonMenu.BUTTON_WIDTH).height(85f).row();

        ButtonMenu menu = new ButtonMenu(tweenManager);
        menu.add("Create", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setScreen(new IngameScreen(game, false));
            }
        });
        layout.add(menu).padTop(30f).row();
        stage.addActor(layout);
    }
}
