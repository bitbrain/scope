package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.FX;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.util.Colors;
import nl.fontys.scope.util.ValueProvider;

public abstract class LoadingScreen implements Screen {

    protected final float FADE_TIME = 0.4f;

    protected ScopeGame game;

    protected SpriteBatch batch;

    protected OrthographicCamera cam;

    private ShapeRenderer renderer;

    protected FX fx = FX.getInstance();

    protected TweenManager tweenManager;

    private BitmapFont font;

    private Label label, progress;

    protected ValueProvider value = new ValueProvider();

    protected float target = 0;

    public LoadingScreen(ScopeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        tweenManager = new TweenManager();
        this.batch = new SpriteBatch();
        cam = new OrthographicCamera();
        renderer = new ShapeRenderer();
        fx.init(tweenManager, null, cam);
    }

    @Override
    public void render(float delta) {
        if (AssetManager.isLoaded(Assets.Fonts.OPENSANS_MEDIUM_32.getPath())) {
            font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        }
        tweenManager.update(delta);
        cam.update();
        Gdx.gl.glClearColor(Colors.BACKGROUND.r, Colors.BACKGROUND.g, Colors.BACKGROUND.b, 1f);
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
        final float WIDTH_LIMIT = 500f;
        float width = Gdx.graphics.getWidth() * 0.8f;
        if (width > WIDTH_LIMIT) {
            width = WIDTH_LIMIT;
        }
        float height = 4f;
        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Colors.lighten(Colors.PRIMARY, 0.3f));
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
            progressStyle.fontColor = Colors.PRIMARY;
            progressStyle.font = font;
            label = new Label(getLabelKey(), style);
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

    protected abstract String getLabelKey();
}
