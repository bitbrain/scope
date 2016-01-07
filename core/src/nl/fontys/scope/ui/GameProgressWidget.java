package nl.fontys.scope.ui;

import com.badlogic.gdx.Gdx;
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

public class GameProgressWidget extends Actor {

    private static final float BORDER_PADDING = Config.UI_BAR_PADDING * 3f;

    private Player player;

    private TweenManager tweenManager;

    private NinePatch border, fill;

    private Label name;

    private ValueProvider valueProvider;

    private ValueProvider alphaProvider;

    private float lastProgress;

    public GameProgressWidget(Player player, TweenManager tweenManager) {
        this.player = player;
        this.tweenManager = tweenManager;
        valueProvider = new ValueProvider();
        valueProvider.setValue(this.player.getGameProgress());
        alphaProvider = new ValueProvider();
        alphaProvider.setValue(0.1f);
        lastProgress = this.player.getGameProgress();
        setHeight(Gdx.graphics.getHeight() / 2.4f);
        setWidth(getHeight() / 3f);
        border = GraphicsFactory.createNinePatch(Assets.Textures.BORDER, 20);
        border.setColor(Colors.UI.cpy());
        border.getColor().a = 0.8f;
        fill = GraphicsFactory.createNinePatch(Assets.Textures.FILL, 10);
        fill.setColor(Colors.UI.cpy());
        fill.getColor().a = 0.1f;
        name = new Label(Bundle.general.get(Messages.PLAYER) + " " + player.getNumber(), Styles.LABEL_PLAYER_NAME);
        name.getColor().a = 0.5f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ActorShadow.draw(batch, this);
        if (lastProgress != player.getGameProgress()) {
            lastProgress = player.getGameProgress();
            tweenManager.killTarget(valueProvider);
            tweenManager.killTarget(alphaProvider);
            alphaProvider.setValue(1f);
            Tween.to(valueProvider, ValueTween.VALUE, 1.5f).ease(TweenEquations.easeOutCubic).target(player.getGameProgress()).start(tweenManager);
            Tween.to(alphaProvider, ValueTween.VALUE, 1.5f).ease(TweenEquations.easeOutCubic).target(0.2f).start(tweenManager);
            if (player.getGameProgress() == 1f) {
                fill.setColor(Colors.ACTIVE.cpy());
                border.setColor(Colors.ACTIVE.cpy());
                name.setColor(Colors.ACTIVE.cpy());
                name.getColor().a = 0.9f;
            }
        }
        border.getColor().a = 0.6f + alphaProvider.getValue();
        border.draw(batch, getX(), getY(), getWidth(), getHeight());
        final float FILL_HEIGHT = getHeight() - BORDER_PADDING * 2;
        fill.getColor().a = 0.1f;
        fill.draw(batch, getX() + BORDER_PADDING, getY() + BORDER_PADDING, getWidth() - BORDER_PADDING * 2, FILL_HEIGHT);
        if (valueProvider.getValue() > 0f) {
            fill.getColor().a = alphaProvider.getValue();
            fill.draw(batch, getX() + BORDER_PADDING, getY() + BORDER_PADDING, getWidth() - BORDER_PADDING * 2, FILL_HEIGHT * valueProvider.getValue());
        }
        name.setPosition(getX() + getWidth() / 2f - name.getPrefWidth() / 2f, getY() + getHeight() + 5f);
        name.draw(batch, parentAlpha);
    }
}
