package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.Config;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.FX;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.tweens.ValueTween;
import nl.fontys.scope.ui.Styles;
import nl.fontys.scope.util.Colors;
import nl.fontys.scope.util.ValueProvider;

public class LoadingScreen implements Screen {

    private final float FADE_TIME = 0.4f;

    private ScopeGame game;

    private SpriteBatch batch;

    private OrthographicCamera cam;

    private ShapeRenderer renderer;

    private FX fx = FX.getInstance();

    private TweenManager tweenManager;

    private BitmapFont font;

    private Label label, progress;

    private ContextProvider contextProvider;

    private ValueProvider value = new ValueProvider();

    private float target = 0;

    private boolean loaded = false;

    public LoadingScreen(ScopeGame game, String[] args) {
        this(args);
        this.game = game;
    }

    public LoadingScreen(String[] args) {
        contextProvider = new ContextProvider(args);
    }

    @Override
    public void show() {
        tweenManager = new TweenManager();
        AssetManager.init();
        this.batch = new SpriteBatch();
        cam = new OrthographicCamera();
        renderer = new ShapeRenderer();
        fx.init(tweenManager, cam);
        fx.fadeIn(FADE_TIME, TweenEquations.easeInCubic);
        Bundle.load();
    }

    @Override
    public void render(float delta) {
        if (AssetManager.isLoaded(Assets.Fonts.OPENSANS_MEDIUM_32.getPath())) {
            font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        }
        if (target < AssetManager.getProgress()) {
            target = AssetManager.getProgress();
            Tween.to(value, ValueTween.VALUE, FADE_TIME).target(target).ease(TweenEquations.easeOutCubic).start(tweenManager);
        }
        tweenManager.update(delta);
        cam.update();
        if (AssetManager.isLoaded() && !loaded) {
            loaded = true;
            Tween.to(value, ValueTween.VALUE, FADE_TIME).target(1f).ease(TweenEquations.easeOutCubic).
                   setCallbackTriggers(TweenCallback.COMPLETE)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            Styles.init();
                            game.setScreen(contextProvider.getScreen());
                        }
                    }).start(tweenManager);
            fx.fadeOut(FADE_TIME, TweenEquations.easeOutCubic);
        } else {
            AssetManager.update();
        }
        Gdx.gl.glClearColor(Colors.SECONDARY.r, Colors.SECONDARY.g, Colors.SECONDARY.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        drawLoadingBar();
        drawForeground(delta);
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
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

    private void drawLoadingBar() {
        float width = Gdx.graphics.getWidth() * 0.8f;
        float height = 2f;
        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Colors.lighten(Colors.SECONDARY, 3f));
        renderer.rect(Gdx.graphics.getWidth() / 2f - width / 2f, Gdx.graphics.getHeight() / 2f - height / 2f, width, height);
        renderer.setColor(Colors.PRIMARY);
        renderer.rect(Gdx.graphics.getWidth() / 2f - width / 2f, Gdx.graphics.getHeight() / 2f - height / 2f, width * value.getValue(), height);
        renderer.end();
    }

    private void drawForeground(float delta) {

        batch.begin();

        if (font != null && label == null) {
            Label.LabelStyle style = new Label.LabelStyle();
            style.fontColor = Colors.PRIMARY;
            style.font = font;
            Label.LabelStyle progressStyle = new Label.LabelStyle();
            progressStyle.fontColor = Colors.lighten(Colors.SECONDARY, 8f);
            progressStyle.font = font;
            label = new Label(Bundle.general.get(Messages.LOADING_INFO), style);
            progress = new Label("0%", progressStyle);
        }
        if (label != null) {
            label.setPosition(Gdx.graphics.getWidth() / 2f - label.getPrefWidth() / 2f, Gdx.graphics.getHeight() / 2f - label.getPrefHeight() / 2f + label.getPrefHeight());
            label.draw(batch, 1f);
            progress.setText(Math.round(value.getValue() * 100f) + "%");
            progress.setPosition(Gdx.graphics.getWidth() / 2f - progress.getPrefWidth() / 2f, Gdx.graphics.getHeight() / 2f - progress.getPrefHeight() / 2f - progress.getPrefHeight() / 1.5f);
            progress.draw(batch, 1f);

        }
        fx.render(batch, delta);
        batch.end();
    }

    private class ContextProvider {

        private boolean debug;

        public ContextProvider(String[] args) {
            for (String arg : args) {
                if (Config.FLAG_DEBUG.equals(arg)) {
                    debug = true;
                    break;
                }
            }
        }

        public Screen getScreen() {
            if (debug) {
                return new IngameScreen(game, true);
            } else {
                return new MenuScreen(game);
            }
        }

    }
}
