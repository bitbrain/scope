package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.controls.KeyboardControls;
import nl.fontys.scope.core.GameObject;
import nl.fontys.scope.core.GameObjectType;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.CameraController;
import nl.fontys.scope.core.controller.RingController;
import nl.fontys.scope.core.controller.ShipController;
import nl.fontys.scope.graphics.EnvironmentCubemap;

public class IngameScreen implements Screen {

    private World world;

    private KeyboardControls keyboardControls;

    private InputMultiplexer multiplexer;

    private CameraController camController;

    @Override
    public void show() {
        world = new World();
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        GameObject ship = world.createGameObject();
        ship.setType(GameObjectType.SHIP);
        ShipController controller = new ShipController();
        world.setController(ship, controller);
        keyboardControls = new KeyboardControls(controller);
        camController = new CameraController(ship, world.getCamera());
    }

    @Override
    public void render(float delta) {
        keyboardControls.update(delta);
        camController.update(delta);
        Gdx.gl.glClearColor(0.02f, 0f, 0.05f, 1f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        world.updateAndRender(delta);
    }

    @Override
    public void resize(int width, int height) {
        world.resize(width, height);
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
        world.dispose();
    }
}
