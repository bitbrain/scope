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
import nl.fontys.scope.core.controller.RingController;
import nl.fontys.scope.core.controller.ShipController;
import nl.fontys.scope.graphics.EnvironmentCubemap;

public class IngameScreen implements Screen {

    private World world;

    private KeyboardControls keyboardControls;

    private InputMultiplexer multiplexer;

    private CameraInputController camController;

    @Override
    public void show() {
        world = new World();
        camController = new CameraInputController(world.getCamera()) {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }
        };
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(camController);
        Gdx.input.setInputProcessor(multiplexer);
        GameObject ship = world.createGameObject();
        ship.setType(GameObjectType.SHIP);
        ship.getOrientation().setEulerAngles(70f, 0f, 0f);
        ShipController controller = new ShipController();
        world.setController(ship, controller);
        keyboardControls = new KeyboardControls(controller);

        final int rings = 6;
        for (int i = 0; i < rings; ++i) {
            GameObject ring = world.createGameObject();
            ring.setScale(100f * (i * i + 1));
            ring.setType(GameObjectType.RING);
            world.setController(ring, new RingController());
        }
    }

    @Override
    public void render(float delta) {
        keyboardControls.update(delta);
        camController.update();
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
