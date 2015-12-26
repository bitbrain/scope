package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.tweens.ValueTween;
import nl.fontys.scope.util.Colors;
import nl.fontys.scope.util.ValueProvider;

public class LifeWidget extends Actor {

    private static final float BAR_PADDING = 4f;

    private NinePatch background;

    private Player player;

    private ValueProvider alphaValueProvider, healthValueProvider;

    private float lastHealth;

    private TweenManager tweenManager;

    private FocusValueWidget focusWidget;

    private Label lifeInfo;

    public LifeWidget(Player player, TweenManager tweenManager) {
        this.player = player;
        focusWidget = new FocusValueWidget(player, tweenManager);
        background = GraphicsFactory.createNinePatch(Assets.Textures.BAR_SMALL, 5);
        lifeInfo = new Label("life", Styles.LABEL_DESCRIPTION);
        background.setColor(Colors.UI.cpy());
        alphaValueProvider = new ValueProvider();
        healthValueProvider = new ValueProvider();
        lastHealth = this.player.getHealth();
        healthValueProvider.setValue(lastHealth);
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
        if (lastHealth != player.getHealth()) {
            lastHealth = player.getHealth();
            tweenManager.killTarget(alphaValueProvider);
            tweenManager.killTarget(healthValueProvider);
            alphaValueProvider.setValue(1f);
            Tween.to(alphaValueProvider, ValueTween.VALUE, 1.0f).target(0f).ease(TweenEquations.easeOutCubic).start(tweenManager);
            Tween.to(healthValueProvider, ValueTween.VALUE, 0.5f).target(player.getHealth()).ease(TweenEquations.easeOutCubic).start(tweenManager);

        }
        background.getColor().a = parentAlpha * 0.1f;
        background.draw(batch, getX(), getY(), getWidth(), getHeight());
        background.getColor().a = parentAlpha * (0.1f + alphaValueProvider.getValue());
        background.draw(batch, getX() + BAR_PADDING, getY() + BAR_PADDING, (getWidth() - BAR_PADDING * 2) * healthValueProvider.getValue(), getHeight() - BAR_PADDING * 2);

        focusWidget.setPosition(getX() + getWidth() / 2f - focusWidget.getPrefWidth() / 2f, getY() - focusWidget.getPrefHeight() - 5f);
        focusWidget.draw(batch, parentAlpha);
        lifeInfo.getColor().a = parentAlpha * (0.5f + alphaValueProvider.getValue());
        lifeInfo.setPosition(getX() + getWidth() / 2f - lifeInfo.getPrefWidth() / 2f, getY() + getHeight() + 5f);
        lifeInfo.draw(batch, parentAlpha);

    }
}
