package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;
import com.bitfire.postprocessing.effects.Zoomer;
import com.badlogic.gdx.math.Vector3;

import net.engio.mbassy.listener.Handler;

import java.util.UUID;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.core.World;
import nl.fontys.scope.tweens.ColorTween;
import nl.fontys.scope.tweens.PointLightTween;
import nl.fontys.scope.tweens.SpriteTween;
import nl.fontys.scope.tweens.ValueTween;
import nl.fontys.scope.tweens.ZoomerShaderTween;
import nl.fontys.scope.util.Colors;
import nl.fontys.scope.util.ValueProvider;

public final class FX {

    private static final FX INSTANCE = new FX();

    private TweenManager tweenManager;

    private LightingManager lightingManager;

    private World world;

    private Sprite flash;

    private OrthographicCamera camera;

    private Color flashColor;

    private Events events = Events.getInstance();

    private ScreenShaker screenShaker;

    static {
        Tween.registerAccessor(Sprite.class, new SpriteTween());
        Tween.registerAccessor(Color.class, new ColorTween());
        Tween.registerAccessor(ValueProvider.class, new ValueTween());
        Tween.registerAccessor(Zoomer.class, new ZoomerShaderTween());
        Tween.registerAccessor(PointLight.class, new PointLightTween());
    }

    private FX() {
        flash = new Sprite(GraphicsFactory.createTexture(1, 1, Color.BLACK));
        flash.setAlpha(0f);
        flashColor = Color.WHITE.cpy();
    }

    public static FX getInstance() {
        return INSTANCE;
    }

    public void setFadeColor(Color color) {
        flashColor = color.cpy();
    }

    public void init(TweenManager tweenManager, World world, OrthographicCamera camera) {
        this.tweenManager = tweenManager;
        this.world = world;
        this.camera = camera;
        if (world != null) {
            this.lightingManager = world.getLightingManager();
        }
        events.register(this);
        screenShaker = new ScreenShaker(tweenManager);
    }

    public void begin() {
        Vector3 vec = screenShaker.getShake();
        if (world != null) {
            PerspectiveCamera camera = world.getCamera();
            camera.translate(vec.x, vec.y, vec.z);
        }
    }

    public void end() {
        Vector3 vec = screenShaker.getShake();
        if (world != null) {
            PerspectiveCamera camera = world.getCamera();
            camera.translate(-vec.x, -vec.y, -vec.z);
        }
    }

    public void render(Batch batch, float delta) {
        flash.setPosition(camera.position.x - (camera.zoom * camera.viewportWidth) / 2, camera.position.y
                - (camera.zoom * camera.viewportHeight) / 2);
        flash.setSize(camera.viewportWidth * camera.zoom, camera.viewportHeight * camera.zoom);
        flash.setColor(flashColor.r, flashColor.g, flashColor.b, flash.getColor().a);
        flash.draw(batch, 1f);
    }

    public void shake(float strength, float duration) {
        screenShaker.shake(strength, duration, 0f);
    }

    public void fadeIn(float duration, TweenEquation equation) {
        flash.setAlpha(1f);
        tweenManager.killTarget(flash);
        Tween.to(flash, SpriteTween.ALPHA, duration).target(0f).ease(equation).start(tweenManager);
    }

    public void fadeOut(float duration) {
        fadeOut(duration, TweenEquations.easeInQuad);
    }

    public void fadeOut(float duration, TweenEquation equation) {
        fadeOut(duration, equation, null);
    }

    public void fadeOut(float duration, TweenEquation equation, TweenCallback callback) {
        flash.setAlpha(0f);
        tweenManager.killTarget(flash);
        Tween tween = Tween.to(flash, SpriteTween.ALPHA, duration).target(1f).ease(equation);
        if (callback != null) {
            tween.setCallbackTriggers(TweenCallback.COMPLETE).setCallback(callback);
        }
        tween.start(tweenManager);
    }

    public void fadeIn(float duration) {
        fadeIn(duration, TweenEquations.easeInQuad);
    }

    public void explosion(Vector3 position) {
        // Show zoom effect
        ShaderManager shaderManager = ShaderManager.getBaseInstance();
        tweenManager.killTarget(shaderManager.zoomer);
        shaderManager.zoomer.setZoom(1f);
        shaderManager.zoomer.setBlurStrength(0f);
        Tween.to(shaderManager.zoomer, ZoomerShaderTween.ZOOM, 0.6f).target(1.01f).repeatYoyo(1, 0).ease(TweenEquations.easeInCubic).start(tweenManager);
        Tween.to(shaderManager.zoomer, ZoomerShaderTween.BLUR_STRENGTH, 0.6f).target(0.4f).repeatYoyo(1, 0).ease(TweenEquations.easeInCubic).start(tweenManager);
        if (lightingManager != null) {
            final PointLight light = new PointLight().set(Colors.ACTIVE, position, 0);
            final String lightId = UUID.randomUUID().toString();
            lightingManager.addPointLight(lightId, light);
            Tween.to(light, PointLightTween.INTENSITY, 1.5f).target(15).repeatYoyo(1, 0).ease(TweenEquations.easeInCubic).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(new TweenCallback() {

                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    lightingManager.removePointLight(lightId);
                }
            }).start(tweenManager);
        }
        ParticleManager.getInstance().create(position, Assets.ParticleEffects.EXPLOSION);
        screenShaker.shake(0.6f, 2f, 0.35f);
    }

    @Handler
    public void onEvent(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.PLAYER_SHIP_DESTROYED)) {
            GameObject playerShip = (GameObject) event.getPrimaryParam();
            if (!PlayerManager.getCurrent().getShip().equals(playerShip)) {
                explosion(playerShip.getPosition().cpy());
            }
        }
    }
}