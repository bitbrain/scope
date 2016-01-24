package nl.fontys.scope.screens;

import com.badlogic.gdx.audio.Music;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.tweens.ValueTween;

public class LoadingAssetsScreen extends LoadingScreen {

    protected boolean loaded = false;

    public LoadingAssetsScreen(ScopeGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        AssetManager.init();
        fx.fadeIn(FADE_TIME, TweenEquations.easeInCubic);
        Bundle.load();
    }

    @Override
    public void render(float delta) {
        if (AssetManager.isLoaded() && !loaded) {
            loaded = true;
            value.setValue(1f);
            super.render(delta);
            game.setScreen(new CompilingShadersScreen(game));
        } else {
            if (AssetManager.isLoaded(Assets.Musics.MAIN_THEME.getPath())) {
                Music music = AssetManager.getMusic(Assets.Musics.MAIN_THEME);
                if (!music.isPlaying()) {
                    music.setLooping(true);
                    music.play();
                }
            }
            if (target < AssetManager.getProgress()) {
                target = AssetManager.getProgress();
                Tween.to(value, ValueTween.VALUE, FADE_TIME).target(target).ease(TweenEquations.easeOutCubic).start(tweenManager);
            }
            AssetManager.update();
            super.render(delta);
        }
    }

    @Override
    protected String getLabelKey() {
        return textProvider.getNextText() + "...";
    }
}