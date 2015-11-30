package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;

public class LoadingScreen implements Screen {

    private ScopeGame game;

    private Stage stage;

    private SpriteBatch batch;

    private OrthographicCamera cam;

    private ShapeRenderer renderer;

    public LoadingScreen(ScopeGame game) {
        this.game = game;
    }

    public LoadingScreen() {

    }

    @Override
    public void show() {
        AssetManager.init();
        this.batch = new SpriteBatch();
        cam = new OrthographicCamera();
        renderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        float height = 5f;
        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GRAY);
        renderer.rect(Gdx.graphics.getWidth() / 2f - width / 2f, Gdx.graphics.getHeight() / 2f - height / 2f, width, height);
        renderer.setColor(Color.RED);
        renderer.rect(Gdx.graphics.getWidth() / 2f - width / 2f, Gdx.graphics.getHeight() / 2f - height / 2f, width * AssetManager.getProgress(), height);
        renderer.end();
    }

    @Override
    public void resize(int width, int height) {
        if (stage == null) {
            stage = new Stage(new ScreenViewport());
        }
        cam.setToOrtho(true, width, height);
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
