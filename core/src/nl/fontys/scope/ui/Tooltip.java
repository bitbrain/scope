package nl.fontys.scope.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.HashSet;
import java.util.Set;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.tweens.ActorTween;
import nl.fontys.scope.util.Colors;

public class Tooltip {

    private static final Tooltip instance = new Tooltip();

    private static final float SHADOW_MARGIN = 64f;

    private Stage stage;

    private Camera camera;

    private Set<Label> tooltips = new HashSet<Label>();

    private TweenEquation equation;

    private float duration;

    private float scale;

    private TweenManager tweenManager;

    private NinePatch shadowPatch;

    static {
        Tween.registerAccessor(Actor.class, new ActorTween());
    }

    private Tooltip() {
        shadowPatch = GraphicsFactory.createNinePatch(Assets.Textures.SHADOW, 80);
        shadowPatch.setColor(Colors.lighten(Colors.BACKGROUND, 0.4f));
        setTweenEquation(TweenEquations.easeOutCubic);
        duration = 2.5f;
        scale = 1.0f;
    }

    public static Tooltip getInstance() {
        return instance;
    }

    public void create(Label.LabelStyle style, String text) {
        create(0f, style, text);
    }

    public void create(float verticaOffset, Label.LabelStyle style, String text) {
        Label tooltip = createInternally(0f, 0f, style, text, Color.WHITE);
        final float x = Gdx.graphics.getWidth() / 2f - tooltip.getPrefWidth() / 2f;
        final float y = Gdx.graphics.getHeight() / 2f - tooltip.getPrefHeight() / 2f + verticaOffset;
        tooltip.setPosition(x, y);
    }

    public void create(float x, float y, Label.LabelStyle style, String text) {
        create(x, y, style, text, Color.WHITE);
    }

    public void create(float x, float y, Label.LabelStyle style, String text, Color color) {
        createInternally(x, y, style, text, color);
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setTweenEquation(TweenEquation equation) {
        this.equation = equation;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void clear() {
        for (Label l : tooltips) {
            tweenManager.killTarget(l);
            stage.getActors().removeValue(l, true);
        }
        tooltips.clear();
    }

    public void init(Stage stage, Camera camera, TweenManager tweenManager) {
        this.stage = stage;
        this.camera = camera;
        this.tweenManager = tweenManager;
    }

    private Label createInternally(float x, float y, Label.LabelStyle style, String text, Color color) {
        final Label tooltip = new Label(text, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                shadowPatch.getColor().a = 1.2f * getColor().a;
                shadowPatch.draw(batch, getX() - SHADOW_MARGIN, getY() - SHADOW_MARGIN, getWidth() + SHADOW_MARGIN * 2f, getHeight() + SHADOW_MARGIN * 2f);
                super.draw(batch, parentAlpha);
            }
        };
        tooltip.setZIndex(100);
        tooltip.setTouchable(Touchable.disabled);
        tooltip.setColor(color.cpy());
        tooltip.setPosition(x, y);
        stage.addActor(tooltip);
        tooltips.add(tooltip);
        Tween.to(tooltip, ActorTween.ALPHA, this.duration).target(0f).setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        stage.getActors().removeValue(tooltip, true);
                    }
                }).ease(equation).start(tweenManager);
        Tween.to(tooltip, ActorTween.SCALE, this.duration).target(scale).ease(equation).start(tweenManager);
        return tooltip;
    }

}
