package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;

public final class GraphicsFactory {

    public static Texture createTexture(int width, int height, Color color) {
        Pixmap map = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        map.setColor(color);
        map.fill();
        Texture texture = new Texture(map);
        map.dispose();
        return texture;
    }

    public static NinePatch createNinePatch(Assets.Textures texture, int radius) {
        return new NinePatch(AssetManager.getTexture(texture), radius, radius, radius, radius);
    }

    public static Drawable createDrawable(Assets.Textures texture, Color color) {
        NinePatch patch = GraphicsFactory.createNinePatch(texture, 40);
        patch.setColor(color.cpy());
        return new NinePatchDrawable(patch);
    }
}