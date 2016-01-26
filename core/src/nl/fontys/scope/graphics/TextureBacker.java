package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.HashMap;
import java.util.Map;

public class TextureBacker {

    public static interface Backeable {
        Texture back(Batch batch, float alphaModulation);
    }

    private Map<String, Backeable> backeables = new HashMap<String, Backeable>();

    private Map<String, Texture> textures = new HashMap<String, Texture>();

    public void render(Batch batch, float alphaModulation) {
        for (Map.Entry<String, Backeable> entry : backeables.entrySet()) {
            Texture texture = entry.getValue().back(batch, alphaModulation);
            textures.put(entry.getKey(), texture);
        }
    }

    public void register(String id, Backeable backeable) {
        unregister(id);
        backeables.put(id, backeable);
    }

    public void unregister(String id) {
        if (textures.containsKey(id)) {
            textures.get(id).dispose();
            backeables.remove(id);
        }
    }

    public Texture getBackedTexture(String id) {
        return textures.get(id);
    }
}
