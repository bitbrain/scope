package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.concurrent.Executors;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.util.Colors;

public class CompilingShadersScreen implements Screen {

    private ScopeGame game;

    private ShaderManager shaderManager;



    public CompilingShadersScreen(ScopeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                shaderManager = ShaderManager.getBaseInstance();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Colors.BACKGROUND.r, Colors.BACKGROUND.g, Colors.BACKGROUND.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (shaderManager != null && shaderManager.isCompiled()) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
