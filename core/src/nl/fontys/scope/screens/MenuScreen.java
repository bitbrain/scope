package nl.fontys.scope.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import nl.fontys.scope.Config;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.controller.CameraRotatingController;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.Styles;

public class MenuScreen extends AbstractScreen {

    private ShaderManager shaderManager = ShaderManager.getBaseInstance();

    public MenuScreen(ScopeGame game) {
        super(game);
    }

    @Override
    protected void onShow() {
        GameObject planet = factory.createPlanet(0f, 50f, 0f, 0f);
        world.addController(new CameraRotatingController(500f, world.getCamera(), planet));
    }

    @Override
    protected void onUpdate(float delta) {

    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);
        Image logo = new Image(new Sprite(AssetManager.getTexture(Assets.Textures.LOGO)));
        layout.center().add(logo).padBottom(20f);
        layout.row();
        ButtonMenu menu = new ButtonMenu(tweenManager);
        menu.add(Bundle.general.get(Messages.MENU_NEW_GAME), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new IngameScreen(game, false));
            }
        });
        menu.add(Bundle.general.get(Messages.MENU_JOIN_GAME), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO
            }
        });
        layout.add(menu).row();
        Label version = new Label("version " + Config.APP_VERSION, Styles.LABEL_VERSION);
        layout.row();
        layout.add(version);
        Label credits = new Label(Bundle.general.get(Messages.CREDITS), Styles.LABEL_CREDITS);
        layout.row();
        layout.add(credits);
        stage.addActor(layout);
    }
}
