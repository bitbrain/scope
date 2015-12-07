package nl.fontys.scope.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.controller.CameraRotatingController;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.object.GameObject;

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
        layout.center().add(logo);
        stage.addActor(layout);
    }
}
