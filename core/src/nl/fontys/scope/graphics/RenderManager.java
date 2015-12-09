package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.renderer.ModelInstanceProvider;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

/**
 * Manages rendering of the game
 */
public class RenderManager {

    private LightingManager lightingManager;

    private ModelBatch modelBatch = new ModelBatch();

    private EnvironmentCubemap cubemap;

    private Map<GameObjectType, ModelInstanceProvider> providers = new HashMap<GameObjectType, ModelInstanceProvider>();

    public RenderManager(LightingManager lightingManager) {
        this.lightingManager = lightingManager;
        TextureData data = AssetManager.getTexture(Assets.Textures.CUBEMAP_SPACE_1).getTextureData();
        data.prepare();
        cubemap = new EnvironmentCubemap(data.consumePixmap());
    }

    public void register(GameObjectType type, ModelInstanceProvider renderer) {
        this.providers.put(type, renderer);
    }

    public void background(Camera camera) {
        cubemap.render(camera);
    }

    public void render(GameObject object, Camera camera) {
        modelBatch.begin(camera);
        if (providers.containsKey(object.getType())) {
            ModelInstanceProvider provider = providers.get(object.getType());
            modelBatch.render(provider.get(object), provider.hasLighting() ? lightingManager.getEnvironment() : null);
        }
        modelBatch.end();
    }
}
