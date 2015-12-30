package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.audio.SoundManager;
import nl.fontys.scope.tweens.ColorTween;

public class ButtonMenu extends Table {

    public static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 90f;
    public static final float PADDING = 10f;
    private static final float ALPHA = 0.5f;

    private TweenManager tweenManager;

    public ButtonMenu(TweenManager tweenManager) {
        this.tweenManager = tweenManager;
        setTouchable(Touchable.childrenOnly);
    }

    public Button add(String caption, final ClickListener listener) {
        final TextButton button = new TextButton(caption, Styles.BUTTON_MENU);
        button.setColor(new Color(1f, 1f, 1f, ALPHA));
        button.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!button.isDisabled()) {
                    listener.clicked(event, x, y);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!button.isDisabled()) {
                    listener.enter(event, x, y, pointer, fromActor);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (!button.isDisabled()) {
                    listener.exit(event, x, y, pointer, toActor);
                }
            }
        });
        center().add(button).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(PADDING);
        row();
        button.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!button.isDisabled()) {
                    SoundManager.getInstance().play(Assets.Sounds.MENU_SELECT, 1f, 1f, 0f);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!button.isDisabled()) {
                    super.enter(event, x, y, pointer, fromActor);
                    tweenManager.killTarget(button);
                    Tween.to(button.getColor(), ColorTween.A, 1.0f).target(1f).ease(TweenEquations.easeOutCubic).start(tweenManager);
                    SoundManager.getInstance().play(Assets.Sounds.MENU_HOVER, 0.4f, 1f, 0f);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (!button.isDisabled()) {
                    super.exit(event, x, y, pointer, toActor);
                    if (event.getRelatedActor() == null || (!event.getRelatedActor().equals(button) &&
                            event.getRelatedActor() instanceof TextButton)) {
                        tweenManager.killTarget(button);
                        Tween.to(button.getColor(), ColorTween.A, 1.0f).target(ALPHA).ease(TweenEquations.easeOutCubic).start(tweenManager);
                    }
                }
            }
        });
        return button;
    }
}
