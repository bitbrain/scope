package nl.fontys.scope.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;

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

    public static void init() {
        for (Assets.Textures texture : Assets.Textures.values()) {
            assetManager.load(texture.getPath(), Texture.class);
        }
        for (Assets.Models model : Assets.Models.values()) {
            assetManager.load(model.getPath(), Model.class);
        }
        assetManager.finishLoading();
    }
}
