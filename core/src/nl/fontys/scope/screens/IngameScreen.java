package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import nl.fontys.scope.controls.KeyboardControls;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.object.GameObjectType;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.CameraController;
import nl.fontys.scope.core.controller.ShipController;

public class IngameScreen implements Screen {

    private World world;

    private KeyboardControls keyboardControls;

    private InputMultiplexer multiplexer;

    private CameraController camController;

    private GameObjectFactory factory;

    @Override
    public void show() {
        world = new World();
        factory = new GameObjectFactory(world);
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        camController = new CameraController(world.getCamera());
        GameObject ship = factory.createShip(0f, 0f, 0f);
        ShipController controller = new ShipController();
        world.addController(ship, controller);
        world.addController(ship, camController);
        keyboardControls = new KeyboardControls(controller);

        GameObject planet = world.createGameObject();
        planet.setType(GameObjectType.PLANET);
        planet.setPosition(1000f, 0f, 0f);
        planet.setScale(100f);

        factory.createArena(0f, 0f, 0f, 2, 500, 1500);
    }

    @Override
    public void render(float delta) {
        keyboardControls.update(delta);
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
