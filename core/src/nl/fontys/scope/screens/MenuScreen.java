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
        shaderManager.bloom.setBaseSaturation(3f);
        shaderManager.bloom.setBaseIntesity(0.4f);
        shaderManager.bloom.setBloomIntesity(4f);
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
        TextButton newGame = new TextButton(Bundle.general.get(Messages.MENU_NEW_GAME), Styles.BUTTON_MENU);
        newGame.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new IngameScreen(game, false));
            }
        });
        layout.center().add(newGame).width(300f).padBottom(10f);
        layout.row();
        TextButton joinGame = new TextButton(Bundle.general.get(Messages.MENU_JOIN_GAME), Styles.BUTTON_MENU);
        layout.center().add(joinGame).width(300f).padBottom(20f);

        Label version = new Label("version " + Config.APP_VERSION, Styles.LABEL_VERSION);
        layout.row();
        layout.add(version);
        Label credits = new Label(Bundle.general.get(Messages.CREDITS), Styles.LABEL_CREDITS);
        layout.row();
        layout.add(credits);
        stage.addActor(layout);
    }
}
