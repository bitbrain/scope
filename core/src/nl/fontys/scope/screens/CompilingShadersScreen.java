package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.graphics.SharedEnvironmentCubemap;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.ui.Styles;

public class CompilingShadersScreen extends LoadingScreen {

    private ShaderManager shaderManager;

    private boolean compiled, initialized;

    public CompilingShadersScreen(ScopeGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        if (!initialized) {
            value.setValue(1f);
            super.render(delta);
            Styles.init();
            SharedEnvironmentCubemap.setup(Assets.Textures.CUBEMAP_SPACE_1);
            initialized = true;
            shaderManager = ShaderManager.getBaseInstance();
        } else {
            super.render(delta);
        }

        if (!compiled && shaderManager != null && shaderManager.isCompiled()) {
            compiled = true;
            fx.fadeOut(FADE_TIME, TweenEquations.easeInCubic, new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    game.setScreen(new MenuScreen(game));
                }
            });
        }
    }

    @Override
    protected String getLabelKey() {
        return Bundle.general.get(Messages.COMPILING_SHADERS_INFO);
    }
}
