package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.audio.SoundManager;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.core.World;

public abstract class AbstractScreen implements Screen {

    protected World world;

    protected InputMultiplexer multiplexer;

    protected GameObjectFactory factory;

    protected SoundManager soundManager = SoundManager.getInstance();

    protected ShaderManager shaderManager;

    private Stage stage;

    protected ScopeGame game;

    public AbstractScreen(ScopeGame game) {
        this.game = game;
    }

    @Override
    public final void show() {
        shaderManager = ShaderManager.getInstance();
        world = new World();
        factory = new GameObjectFactory(world);
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        world.addController(soundManager);
        onShow();
    }

    @Override
    public final void render(float delta) {
        onUpdate(delta);
        Gdx.gl.glClearColor(0.02f, 0f, 0.05f, 1f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        shaderManager.begin();
        world.updateAndRender(delta);
        shaderManager.end();
        stage.act(delta);
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public final void resize(int width, int height) {
        if (stage == null) {
            stage = new Stage();
            multiplexer.addProcessor(stage);
            onCreateStage(stage);
        }
        world.resize(width, height);
    }

    @Override
    public final void pause() {

    }

    @Override
    public final void resume() {
        shaderManager.resume();
    }

    @Override
    public final void hide() {

    }

    @Override
    public final void dispose() {
        world.dispose();
        shaderManager.dispose();
    }

    protected abstract void onShow();
    protected abstract void onUpdate(float delta);
    protected abstract void onCreateStage(Stage stage);
}
