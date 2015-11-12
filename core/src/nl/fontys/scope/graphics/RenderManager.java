package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

/**
 * Manages rendering of the game
 */
public class RenderManager {

    private LightingManager lightingManager;

    private ModelBatch modelBatch = new ModelBatch();

    private EnvironmentCubemap cubemap;

    private Map<GameObjectType, nl.fontys.scope.graphics.renderer.GameObjectRenderer> renderer = new HashMap<GameObjectType, nl.fontys.scope.graphics.renderer.GameObjectRenderer>();

    public RenderManager() {
        lightingManager = new LightingManager();
        Environment environment = lightingManager.getEnvironment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.1f, 0.2f, 0.6f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.5f, 0.1f, -1f, -0.8f, -0.2f));
        environment.add(new DirectionalLight().set(0.0f, 0.4f, 1.0f, -1f, -0.2f, -0.5f));
        TextureData data = AssetManager.getTexture(Assets.Textures.CUBEMAP_SPACE_1).getTextureData();
        data.prepare();
        cubemap = new EnvironmentCubemap(data.consumePixmap());
    }

    public void register(GameObjectType type, nl.fontys.scope.graphics.renderer.GameObjectRenderer renderer) {
        this.renderer.put(type, renderer);
    }

    public void background(Camera camera) {
        cubemap.render(camera);
    }

    public void render(GameObject object, Camera camera) {
        modelBatch.begin(camera);
        if (renderer.containsKey(object.getType())) {
            modelBatch.render(renderer.get(object.getType()).getCurrentInstance(object, lightingManager), lightingManager.getEnvironment());
        }
        modelBatch.end();
    }
}
