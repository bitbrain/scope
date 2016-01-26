package nl.fontys.scope.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.Config;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.audio.SoundManager;
import nl.fontys.scope.controls.ControllerManager;
import nl.fontys.scope.controls.KeyboardMenuSupport;
import nl.fontys.scope.controls.XboxMenuControllerSupport;
import nl.fontys.scope.core.World;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.graphics.FX;
import nl.fontys.scope.graphics.ParticleManager;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.graphics.TextureBacker;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.ModelPreview;
import nl.fontys.scope.ui.Tooltip;

public abstract class AbstractScreen implements Screen {

    private static final boolean isDesktop = (Gdx.app.getType() == Application.ApplicationType.Desktop);

    protected Tooltip tooltip = Tooltip.getInstance();

    protected World world;

    protected InputMultiplexer multiplexer;

    protected GameObjectFactory factory;

    protected SoundManager soundManager = SoundManager.getInstance();

    protected ShaderManager baseShaderManager;

    private Stage stage;

    protected ScopeGame game;

    private FX fx = FX.getInstance();

    protected Events events = Events.getInstance();

    protected ControllerManager controllerManager;

    private KeyboardMenuSupport keyboard;

    public AbstractScreen(ScopeGame game, World world) {
        this.game = game;
        this.world = world;
    }

    public AbstractScreen(ScopeGame game) {
        this(game, new World());
    }

    protected TweenManager tweenManager;

    protected OrthographicCamera cam2D;

    public static FrameBuffer uiBuffer;

    private boolean closing;

    protected TextureBacker textureBacker;

    protected float fadeInTime = 0.4f, fadeOutTime = 0.4f;

    @Override
    public final void show() {
        textureBacker = new TextureBacker();
        controllerManager = new ControllerManager();
        tweenManager = new TweenManager();
        baseShaderManager = ShaderManager.getBaseInstance();
        factory = new GameObjectFactory(world);
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        soundManager.setPerspectiveCamera(world.getCamera());
        world.addLogic(soundManager);
        cam2D = new OrthographicCamera();
        onShow();
    }

    @Override
    public final void render(float delta) {
        onUpdate(delta);
        keyboard.update(delta);
        controllerManager.update(delta);
        tweenManager.update(delta);
        Gdx.gl.glClearColor(0.02f, 0f, 0.05f, 1f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        textureBacker.render(stage.getBatch(), 1f);
        fx.begin();
        cam2D.update();
        if (Config.HIGH_QUALITY) {
            renderHighQuality(delta);
        } else {
            renderLowQuality(delta);
        }
        // Draw special effects (fading etc.)
        stage.getBatch().begin();
        fx.render(stage.getBatch(), delta);
        stage.getBatch().end();
        fx.end();
    }

    private void renderHighQuality(float delta) {
        // Draw world (background)
        ShaderManager.configureBase();
        baseShaderManager.begin();
        world.updateAndRender(closing ? 0f : delta);
        baseShaderManager.end(uiBuffer);
        // Draw UI (foreground)
        ShaderManager.configureUI();
        cam2D.setToOrtho(true);
        cam2D.update();
        stage.getBatch().setProjectionMatrix(cam2D.combined);
        baseShaderManager.begin();
        stage.getBatch().begin();
        stage.getBatch().setColor(Color.WHITE);
        stage.getBatch().draw(uiBuffer.getColorBufferTexture(), 0f, 0f);
        stage.getBatch().end();
        cam2D.setToOrtho(false);
        cam2D.update();
        stage.getBatch().setProjectionMatrix(cam2D.combined);
        stage.act(delta);
        stage.draw();
        baseShaderManager.end(null);
    }

    private void renderLowQuality(float delta) {
        ShaderManager.configureUI();
        baseShaderManager.begin();
        world.updateAndRender(closing ? 0f : delta);
        cam2D.setToOrtho(false);
        cam2D.update();
        stage.getBatch().setProjectionMatrix(cam2D.combined);
        stage.act(delta);
        stage.draw();
        baseShaderManager.end(null);
    }

    @Override
    public final void resize(int width, int height) {
        if (stage == null) {
            stage = new Stage();
            multiplexer.addProcessor(stage);
            tooltip.init(stage, cam2D, tweenManager);
            fx.init(tweenManager, world, cam2D);
            onCreateStage(stage);
            keyboard = new KeyboardMenuSupport(getMenu(), getExitHandler());
            controllerManager.registerSupport(new XboxMenuControllerSupport(getMenu(), getExitHandler()));
            fx.fadeIn(fadeInTime, TweenEquations.easeInCubic);
        }
        updateFrameBuffer(width, height);
        cam2D.setToOrtho(false, width, height);
        world.resize(width, height);
    }

    @Override
    public final void pause() {

    }

    @Override
    public final void resume() {
        baseShaderManager.resume();
        closing = false;
    }

    @Override
    public final void hide() {

    }

    @Override
    public final void dispose() {
        world.dispose();
        events.clear();
        ParticleManager.getInstance().clear();
    }

    public void setScreen(final Screen screen) {
        closing = true;
        Gdx.input.setInputProcessor(null);
        FX.getInstance().fadeOut(fadeOutTime, TweenEquations.easeOutCubic, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                dispose();
                game.setScreen(screen);
            }
        });
    }

    protected abstract void onShow();
    protected abstract void onUpdate(float delta);
    protected abstract void onCreateStage(Stage stage);

    private void updateFrameBuffer(int width, int height) {
        if (uiBuffer == null) {
            uiBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        } else if (uiBuffer.getWidth() != width || uiBuffer.getHeight() != height) {
            uiBuffer.dispose();
            uiBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,width, height, false);
        }
    }

    protected ButtonMenu getMenu() {
        return null;
    }

    protected ExitHandler getExitHandler() {
        return null;
    }
}
