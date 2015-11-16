package nl.fontys.scope.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;

/**
 * Manages different kind of assets
 */
public class AssetManager {

    private static final AssetManager INSTANCE = new AssetManager();

    private static com.badlogic.gdx.assets.AssetManager assetManager = new com.badlogic.gdx.assets.AssetManager();

    private AssetManager() {
        // noOp
    }

    public static Texture getTexture(Assets.Textures textures) {
        return assetManager.get(textures.getPath(), Texture.class);
    }

    public static Model getModel(Assets.Models textures) {
        return assetManager.get(textures.getPath(), Model.class);
    }

    public static Music getMusic(Assets.Musics musics) {
        return assetManager.get(musics.getPath(), Music.class);
    }

    public static Sound getSound(Assets.Sounds sounds) {
        return assetManager.get(sounds.getPath(), Sound.class);
    }

    public static void init() {
        for (Assets.Textures texture : Assets.Textures.values()) {
            assetManager.load(texture.getPath(), Texture.class);
        }
        for (Assets.Models model : Assets.Models.values()) {
            assetManager.load(model.getPath(), Model.class);
        }
        for (Assets.Musics music : Assets.Musics.values()) {
            assetManager.load(music.getPath(), Music.class);
        }
        for (Assets.Sounds sound : Assets.Sounds.values()) {
            // TODO: implement sounds first
            //assetManager.load(sound.getPath(), Sound.class);
        }
        assetManager.finishLoading();
    }

    public static void dispose() {
        assetManager.dispose();
    }
}
