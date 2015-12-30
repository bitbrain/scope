package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.object.GameObject;

/**
 * Manages rendering of the game
 */
public class RenderManager {

    private LightingManager lightingManager;

    private ParticleManager particleManager = ParticleManager.getInstance();

    private ModelBatch modelBatch = new ModelBatch();

    private EnvironmentCubemap cubemap;

    private ModelInstanceService modelInstanceService;

    public RenderManager(LightingManager lightingManager, ModelInstanceService modelInstanceService) {
        this.lightingManager = lightingManager;
        this.modelInstanceService = modelInstanceService;
        cubemap = SharedEnvironmentCubemap.data;
    }

    public void background(Camera camera) {
        cubemap.render(camera);
    }

    public void render(GameObject object, Camera camera) {
        modelBatch.begin(camera);
        if (modelInstanceService.hasModelFor(object)) {
            ModelInstance instance = modelInstanceService.getCurrentInstance(object);
            modelBatch.render(instance, modelInstanceService.isLightingEnabledFor(object) ? lightingManager.getEnvironment() : null);
        }
        modelBatch.end();
    }

    public void particles(Camera camera) {
        particleManager.setCamera(camera);
        particleManager.render(modelBatch);
    }
}
