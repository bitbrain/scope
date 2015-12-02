package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.FX;
import nl.fontys.scope.util.Colors;

public class LoadingScreen implements Screen {

    private ScopeGame game;

    private SpriteBatch batch;

    private OrthographicCamera cam;

    private ShapeRenderer renderer;

    private FX fx = FX.getInstance();

    private TweenManager tweenManager;

    private BitmapFont font;

    private Label label, progress;

    public LoadingScreen(ScopeGame game) {
        this.game = game;
    }

    public LoadingScreen() {

    }

    @Override
    public void show() {
        tweenManager = new TweenManager();
        AssetManager.init();
        this.batch = new SpriteBatch();
        cam = new OrthographicCamera();
        renderer = new ShapeRenderer();
        fx.init(tweenManager, cam);
        fx.fadeIn(0.5f, TweenEquations.easeInOutCubic);
    }

    @Override
    public void render(float delta) {
        if (AssetManager.isLoaded(Assets.Fonts.OPENSANS_MEDIUM_32.getPath())) {
            font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        }
        Gdx.gl.glClearColor(Colors.SECONDARY.r, Colors.SECONDARY.g, Colors.SECONDARY.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tweenManager.update(delta);
        AssetManager.update();
        cam.update();
        if (AssetManager.isLoaded()) {
            game.setScreen(new IngameScreen());
        }
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.end();

        // Draw the loading bar
        float width = Gdx.graphics.getWidth() * 0.8f;
        float height = 2f;
        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Colors.lighten(Colors.SECONDARY, 3f));
        renderer.rect(Gdx.graphics.getWidth() / 2f - width / 2f, Gdx.graphics.getHeight() / 2f - height / 2f, width, height);
        renderer.setColor(Colors.PRIMARY);
        renderer.rect(Gdx.graphics.getWidth() / 2f - width / 2f, Gdx.graphics.getHeight() / 2f - height / 2f, width * AssetManager.getProgress(), height);
        renderer.end();
        batch.begin();

        if (font != null && label == null) {
            Label.LabelStyle style = new Label.LabelStyle();
            style.fontColor = Colors.PRIMARY;
            style.font = font;
            Label.LabelStyle progressStyle = new Label.LabelStyle();
            progressStyle.fontColor = Colors.lighten(Colors.SECONDARY, 3f);
            progressStyle.font = font;
            label = new Label("Loading assets...", style);
            progress = new Label("0%", progressStyle);
        }
        if (label != null) {
            label.setPosition(Gdx.graphics.getWidth() / 2f - label.getPrefWidth() / 2f, Gdx.graphics.getHeight() / 2f - label.getPrefHeight() / 2f + label.getPrefHeight());
            label.draw(batch, 1f);
            progress.setText(Math.round(AssetManager.getProgress() * 100f) + "%");
            progress.setPosition(Gdx.graphics.getWidth() / 2f - progress.getPrefWidth() / 2f, Gdx.graphics.getHeight() / 2f - progress.getPrefHeight() / 2f - progress.getPrefHeight() / 1.5f);
            progress.draw(batch, 1f);

        }
        fx.render(batch, delta);
        batch.end();
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
}
