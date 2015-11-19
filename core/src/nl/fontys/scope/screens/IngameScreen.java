package nl.fontys.scope.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Antialiasing;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.CameraMotion;
import com.bitfire.postprocessing.effects.Fxaa;
import com.bitfire.postprocessing.effects.LensFlare2;
import com.bitfire.postprocessing.effects.MotionBlur;
import com.bitfire.postprocessing.effects.Vignette;
import com.bitfire.postprocessing.effects.Zoomer;
import com.bitfire.postprocessing.filters.RadialBlur;
import com.bitfire.utils.ShaderLoader;

import java.security.SecureRandom;
import java.util.UUID;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.audio.SoundManager;
import nl.fontys.scope.controls.KeyboardControls;
import nl.fontys.scope.core.controller.PlanetController;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.object.GameObjectType;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.CameraController;
import nl.fontys.scope.core.controller.ShipController;

public class IngameScreen implements Screen {

    private static final boolean isDesktop = (Gdx.app.getType() == Application.ApplicationType.Desktop);

    private World world;

    private KeyboardControls keyboardControls;

    private InputMultiplexer multiplexer;

    private CameraController camController;

    private GameObjectFactory factory;

    private SoundManager soundManager = SoundManager.getInstance();

    private PostProcessor postProcessor;

    @Override
    public void show() {
        ShaderLoader.BasePath = "postprocessing/shaders/";
        postProcessor = new PostProcessor( true, true, isDesktop );

        Zoomer zoomer = new Zoomer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), RadialBlur.Quality.High);
        zoomer.setBlurStrength(0.1f);
        zoomer.setZoom(1.1f);
        zoomer.setEnabled(true);
        postProcessor.addEffect(zoomer);
        Vignette vignette = new Vignette(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        vignette.setIntensity(1.0f);
        postProcessor.addEffect(vignette);
        Bloom bloom = new Bloom( (int)(Gdx.graphics.getWidth() * 0.1f), (int)(Gdx.graphics.getHeight() * 0.1f) );
        postProcessor.addEffect(bloom);
        Fxaa fxaa = new Fxaa(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        postProcessor.addEffect(fxaa);
        soundManager.play(Assets.Musics.STARSURFER, true);
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
        SecureRandom random = new SecureRandom(UUID.randomUUID().toString().getBytes());
        world.addController(soundManager);
        factory.createEnergy(random.nextFloat() * 50f - 50f, random.nextFloat() * 50f - 50f, random.nextFloat() * 50f - 50f);
        factory.createPlanet(500f, 50f, 34f, 1.6f);
        factory.createPlanet(1000f, 80f, 234f, 1.2f);
        factory.createPlanet(1500f, 20f, 44f, 2.7f);
        factory.createPlanet(700f, 10f, 44f, 4.7f);

    }

    @Override
    public void render(float delta) {
        keyboardControls.update(delta);
        Gdx.gl.glClearColor(0.02f, 0f, 0.05f, 1f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        postProcessor.capture();
        world.updateAndRender(delta);
        postProcessor.render();
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
        postProcessor.rebind();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        postProcessor.dispose();
    }
}
