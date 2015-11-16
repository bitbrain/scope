package nl.fontys.scope.graphics.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.LightingManager;
import nl.fontys.scope.object.GameObject;

public class ShipRenderer extends ModelRenderer {

    public ShipRenderer() {
        super(AssetManager.getModel(Assets.Models.CRUISER));
    }

    @Override
    public ModelInstance getCurrentInstance(GameObject object, LightingManager lightingManager) {
        PointLight light = lightingManager.getPointLightById(object.getId());
        if (light == null) {
            light = new PointLight();
            lightingManager.addPointLight(object.getId(), light);
        }
        light.set(Color.valueOf("ff7700"), object.getPosition().x, object.getPosition().y, object.getPosition().z, 0f);
        return super.getCurrentInstance(object, lightingManager);
    }
}
