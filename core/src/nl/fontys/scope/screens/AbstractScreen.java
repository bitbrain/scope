package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.audio.SoundManager;
import nl.fontys.scope.graphics.FX;
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

    private FX fx = FX.getInstance();

    public AbstractScreen(ScopeGame game) {
        this.game = game;
    }

    protected TweenManager tweenManager;

    protected OrthographicCamera cam2D;

    @Override
    public final void show() {
        tweenManager = new TweenManager();
        shaderManager = ShaderManager.getInstance();
        world = new World();
        factory = new GameObjectFactory(world);
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        world.addController(soundManager);
        cam2D = new OrthographicCamera();
        onShow();
    }

    @Override
    public final void render(float delta) {
        tweenManager.update(delta);
        onUpdate(delta);
        Gdx.gl.glClearColor(0.02f, 0f, 0.05f, 1f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        cam2D.update();
        shaderManager.begin();
        world.updateAndRender(delta);
        shaderManager.end();
        stage.getBatch().setProjectionMatrix(cam2D.combined);
        stage.act(delta);
        stage.draw();
        stage.getBatch().begin();
        fx.render(stage.getBatch(), delta);
        stage.getBatch().end();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public final void resize(int width, int height) {
        if (stage == null) {
            stage = new Stage();
            multiplexer.addProcessor(stage);
            fx.init(tweenManager, cam2D);
            onCreateStage(stage);
            fx.fadeIn(4f, TweenEquations.easeInCubic);
        }
        cam2D.setToOrtho(false, width, height);
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
