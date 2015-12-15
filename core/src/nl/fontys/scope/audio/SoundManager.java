package nl.fontys.scope.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.controller.GameObjectController;
import nl.fontys.scope.object.GameObject;

public final class SoundManager implements GameObjectController {

    private static final SoundManager INSTANCE = new SoundManager();

    private SoundManager() {
        // noOp
    }

    public static SoundManager getInstance() {
        return INSTANCE;
    }

    public long play(Assets.Sounds sounds, float volume, float pitch, float pan) {
        Sound sound = AssetManager.getSound(sounds);
        return sound.play(volume, pitch, pan);
    }

    public Music play(Assets.Musics musics, boolean loop) {
        Music music = AssetManager.getMusic(musics);
        music.setLooping(loop);
        music.play();
        return music;
    }

    @Override
    public void update(GameObject object, float delta) {

    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {

    }
}
