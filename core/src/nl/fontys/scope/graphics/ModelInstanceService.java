package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

public class ModelInstanceService {

    private Map<GameObjectType, ModelInstanceProvider> providers = new HashMap<GameObjectType, ModelInstanceProvider>();

    public ModelInstanceService() {
        providers.put(nl.fontys.scope.object.GameObjectType.SHIP, new ModelInstanceProvider(AssetManager.getModel(Assets.Models.CRUISER)));
        providers.put(nl.fontys.scope.object.GameObjectType.RING, new ModelInstanceProvider(AssetManager.getModel(Assets.Models.RING)));
        providers.put(nl.fontys.scope.object.GameObjectType.PLANET, new ModelInstanceProvider(AssetManager.getModel(Assets.Models.PLANET)));
        providers.put(nl.fontys.scope.object.GameObjectType.SPHERE, new ModelInstanceProvider(AssetManager.getModel(Assets.Models.PLANET), false));
        providers.put(nl.fontys.scope.object.GameObjectType.ENERGY, new ModelInstanceProvider(AssetManager.getModel(Assets.Models.ENERGY), false));
    }

    public boolean hasModelFor(GameObject object) {
        return providers.containsKey(object.getType());
    }

    public ModelInstance getCurrentInstance(GameObject object) {
        return hasModelFor(object) ? providers.get(object.getType()).get(object) : null;
    }

    public boolean isLightingEnabledFor(GameObject object) {
        return hasModelFor(object) && providers.get(object.getType()).hasLighting();
    }
}
