package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.TextureData;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;

public class SharedEnvironmentCubemap {

    public static EnvironmentCubemap data;

    public static void setup(Assets.Textures textures) {
        TextureData texture = AssetManager.getTexture(textures).getTextureData();
        texture.prepare();
        data = new EnvironmentCubemap(texture.consumePixmap());
    }
}
