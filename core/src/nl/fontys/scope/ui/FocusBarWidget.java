package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.Config;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.tweens.ValueTween;
import nl.fontys.scope.util.Colors;
import nl.fontys.scope.util.ValueProvider;

public class FocusBarWidget extends Actor {

    private NinePatch background;

    private Player player;

    private ValueProvider alphaValueProvider, focusValueProvider;

    private float lastFocus;

    private TweenManager tweenManager;

    private FocusValueWidget focusWidget;

    private Label focusInfo;

    public FocusBarWidget(Player player, TweenManager tweenManager) {
        this.player = player;
        focusWidget = new FocusValueWidget(player, tweenManager);
        background = GraphicsFactory.createNinePatch(Assets.Textures.BAR_SMALL, 5);
        focusInfo = new Label(Bundle.general.get(Messages.FOCUS), Styles.LABEL_DESCRIPTION);
        background.setColor(Colors.UI.cpy());
        alphaValueProvider = new ValueProvider();
        focusValueProvider = new ValueProvider();
        this.tweenManager = tweenManager;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        focusWidget.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        ActorShadow.draw(batch, this);
        if (lastFocus != player.getFocusProgress()) {
            tweenManager.killTarget(alphaValueProvider);
            tweenManager.killTarget(focusValueProvider);
            alphaValueProvider.setValue(1f);
            float duration = player.getFocusProgress() > lastFocus ? 1f : 1.9f;
            lastFocus = player.getFocusProgress();
            Tween.to(alphaValueProvider, ValueTween.VALUE, duration * 1.2f).target(0f).ease(TweenEquations.easeOutCubic).start(tweenManager);
            Tween.to(focusValueProvider, ValueTween.VALUE, duration).target(player.getFocusProgress()).ease(TweenEquations.easeOutCubic).start(tweenManager);
        }
        background.getColor().a = parentAlpha * 0.1f;
        background.draw(batch, getX(), getY(), getWidth(), getHeight());
        if (focusValueProvider.getValue() > 0.04f) {
            background.getColor().a = parentAlpha * (0.2f + alphaValueProvider.getValue());
            final float PROGRESS = Math.min(focusValueProvider.getValue(), 1f);
            background.draw(batch, getX() + Config.UI_BAR_PADDING, getY() + Config.UI_BAR_PADDING, (getWidth() - Config.UI_BAR_PADDING * 2) * PROGRESS, getHeight() - Config.UI_BAR_PADDING * 2);
        }
        focusWidget.setPosition(getX() + getWidth() / 2f - focusWidget.getPrefWidth() / 2f, getY() - focusWidget.getPrefHeight() - 5f);
        focusWidget.draw(batch, parentAlpha);
        focusInfo.getColor().a = parentAlpha * (0.5f + alphaValueProvider.getValue());
        focusInfo.setPosition(getX() + getWidth() / 2f - focusInfo.getPrefWidth() / 2f, getY() + getHeight() + 5f);
        focusInfo.draw(batch, parentAlpha);

    }
}
