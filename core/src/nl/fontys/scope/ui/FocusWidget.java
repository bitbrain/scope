package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.tweens.ValueTween;
import nl.fontys.scope.util.ValueProvider;

public class FocusWidget extends Label {

    private TweenManager tweenManager;

    private Player player;

    private int lastFocus;

    private ValueProvider focusProvider, alphaProvider;

    public FocusWidget(Player player, TweenManager tweenManager) {
        super("0", Styles.LABEL_FOCUS);
        this.player = player;
        this.tweenManager = tweenManager;
        focusProvider = new ValueProvider();
        alphaProvider = new ValueProvider();
        alphaProvider.setValue(0.4f);
        focusProvider.setValue(player.getFocusCount());
        updateFocus();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (lastFocus != player.getFocusCount()) {
            lastFocus = player.getFocusCount();
            updateFocus();
        }
        setText(String.valueOf(Math.round(focusProvider.getValue())) + " focus");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        getColor().a = parentAlpha * alphaProvider.getValue();
        super.draw(batch, parentAlpha);
    }

    private void updateFocus() {
        tweenManager.killTarget(focusProvider);
        tweenManager.killTarget(alphaProvider);
        Tween.to(focusProvider, ValueTween.VALUE, 0.2f).target(player.getFocusCount()).start(tweenManager);
        alphaProvider.setValue(1f);
        Tween.to(alphaProvider, ValueTween.VALUE, 1.5f).target(0.4f).start(tweenManager);
    }
}
