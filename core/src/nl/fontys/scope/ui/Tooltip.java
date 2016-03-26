package nl.fontys.scope.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import nl.fontys.scope.tweens.ActorTween;

public class Tooltip {

    private static final Tooltip instance = new Tooltip();

    private Stage stage;

    private Camera camera;

    private Set<Label> tooltips = new HashSet<Label>();

    private TweenEquation equation;

    private float duration;

    private float scale;

    private TweenManager tweenManager;

    private boolean idle;

    static {
        Tween.registerAccessor(Actor.class, new ActorTween());
    }

    private Tooltip() {
        setTweenEquation(TweenEquations.easeInExpo);
        duration = 3.5f;
        scale = 1.0f;
    }

    public static Tooltip getInstance() {
        return instance;
    }

    public void create(Label.LabelStyle style, String text) {
        create(0f, style, text);
    }

    public void create(Label.LabelStyle style, String text, Color color) {
        create(0f, style, text, color);
    }

    public void create(float verticaOffset, Label.LabelStyle style, String text) {
        create(verticaOffset, style, text, Color.WHITE);
    }

    public void create(float verticaOffset, Label.LabelStyle style, String text, Color color) {
        Label tooltip = createInternally(0f, 0f, style, text, color);
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
        this.idle = true;
    }

    public boolean isIdle() {
        return idle;
    }

    private Label createInternally(float x, float y, Label.LabelStyle style, String text, Color color) {
        final Label tooltip = new Label(text, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                ActorShadow.draw(batch, this, 5);
                super.draw(batch, parentAlpha);
            }
        };
        idle = false;
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
                        idle = tooltips.isEmpty();
                        stage.getActors().removeValue(tooltip, true);
                    }
                }).ease(equation).start(tweenManager);
        Tween.to(tooltip, ActorTween.SCALE, this.duration).target(scale).ease(equation).start(tweenManager);
        return tooltip;
    }

}
