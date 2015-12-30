package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import nl.fontys.scope.Config;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.audio.SoundManager;
import nl.fontys.scope.tweens.ColorTween;

public class ButtonMenu extends Table {

    private TweenManager tweenManager;

    public ButtonMenu(TweenManager tweenManager) {
        this.tweenManager = tweenManager;
        setTouchable(Touchable.childrenOnly);
    }

    public Button add(String caption, final ClickListener listener) {
        final TextButton button = new TextButton(caption, Styles.BUTTON_MENU) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                ActorShadow.draw(batch, this);
                super.draw(batch, parentAlpha);
            }
        };
        button.setColor(new Color(1f, 1f, 1f, Config.MENU_ALPHA));
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
        center().add(button).width(Config.MENU_BUTTON_WIDTH).height(Config.MENU_BUTTON_HEIGHT).padBottom(Config.MENU_PADDING);
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
                        Tween.to(button.getColor(), ColorTween.A, 1.0f).target(Config.MENU_ALPHA).ease(TweenEquations.easeOutCubic).start(tweenManager);
                    }
                }
            }
        });
        return button;
    }
}
