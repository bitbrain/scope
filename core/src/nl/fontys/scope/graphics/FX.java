package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bitfire.postprocessing.effects.Zoomer;

import net.engio.mbassy.listener.Handler;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.screens.GameOverScreen;
import nl.fontys.scope.tweens.ColorTween;
import nl.fontys.scope.tweens.SpriteTween;
import nl.fontys.scope.tweens.ValueTween;
import nl.fontys.scope.tweens.ZoomerShaderTween;
import nl.fontys.scope.util.ValueProvider;

public final class FX {

    private static final FX INSTANCE = new FX();

    private TweenManager tweenManager;

    private Sprite flash;

    private OrthographicCamera camera;

    private Color flashColor;

    private Events events = Events.getInstance();

    static {
        Tween.registerAccessor(Sprite.class, new SpriteTween());
        Tween.registerAccessor(Color.class, new ColorTween());
        Tween.registerAccessor(ValueProvider.class, new ValueTween());
        Tween.registerAccessor(Zoomer.class, new ZoomerShaderTween());
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

    public void init(TweenManager tweenManager, OrthographicCamera camera) {
        this.tweenManager = tweenManager;
        this.camera = camera;
        events.register(this);
    }

    public void render(Batch batch, float delta) {
        flash.setPosition(camera.position.x - (camera.zoom * camera.viewportWidth) / 2, camera.position.y
                - (camera.zoom * camera.viewportHeight) / 2);
        flash.setSize(camera.viewportWidth * camera.zoom, camera.viewportHeight * camera.zoom);
        flash.setColor(flashColor.r, flashColor.g, flashColor.b, flash.getColor().a);
        flash.draw(batch, 1f);
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

    @Handler
    public void onEvent(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.PLAYER_SHIP_DESTROYED)) {
            GameObject playerShip = (GameObject) event.getPrimaryParam();
            if (!PlayerManager.getCurrent().getShip().equals(playerShip)) {
                // Show zoom effect
                ShaderManager shaderManager = ShaderManager.getBaseInstance();
                tweenManager.killTarget(shaderManager.zoomer);
                Tween.to(shaderManager.zoomer, ZoomerShaderTween.ZOOM, 0.45f).target(2f).repeatYoyo(1, 0).ease(TweenEquations.easeInCubic).start(tweenManager);
                Tween.to(shaderManager.zoomer, ZoomerShaderTween.BLUR_STRENGTH, 0.25f).target(15f).repeatYoyo(1, 0).ease(TweenEquations.easeInCubic).start(tweenManager);
            }
        }
    }
}