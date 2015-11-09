package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import nl.fontys.scope.graphics.EnvironmentCubemap;

public class ModelLoadingScreen implements Screen {

    private Model model;

    public PerspectiveCamera cam;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Environment environment;
    public boolean loading;
    public EnvironmentCubemap cubemap;
    private ModelInstance shipInstance;

    public ModelLoadingScreen(Model model) {
        this.model = model;
    }

    @Override
    public void show() {
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.1f, 0.2f, 0.6f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.5f, 0.1f, -1f, -0.8f, -0.2f));
        environment.add(new DirectionalLight().set(0.0f, 0.4f, 1.0f, -1f, -0.2f, -0.5f));
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(1f, 1f, 1f);

        cam.near = 0.2f;
        cam.far = 30000f;
        cam.update();

        camController = new CameraInputController(cam) {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }
        };
        Gdx.input.setInputProcessor(camController);
        loading = true;
        cubemap = new EnvironmentCubemap(new Pixmap(Gdx.files.internal("cubemaps/space1.png")));
        doneLoading();
    }

    @Override
    public void render(float delta) {
        camController.update();
        final float speed = 1f;
        if (shipInstance != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                shipInstance.transform.translate(0f, 0f, speed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                shipInstance.transform.translate(speed, 0f, 0f);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                shipInstance.transform.translate(0f, 0f, -speed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                shipInstance.transform.translate(-speed, 0f, 0f);
            }
            Vector3 pos = shipInstance.transform.getTranslation(new Vector3());
        }

        cam.update();

        Gdx.gl.glClearColor(0.02f, 0f, 0.05f, 1f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        cubemap.render(cam);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
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
        modelBatch.dispose();
        instances.clear();
    }

    private void doneLoading() {
        shipInstance = new ModelInstance(model);
        instances.add(shipInstance);
    }
}
