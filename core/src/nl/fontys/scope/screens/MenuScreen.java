package nl.fontys.scope.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bitfire.postprocessing.filters.Blur;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.core.controller.CameraRotatingController;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.object.GameObject;

public class MenuScreen extends AbstractScreen {

    private ShaderManager shaderManager = ShaderManager.getInstance();

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

    }
}
