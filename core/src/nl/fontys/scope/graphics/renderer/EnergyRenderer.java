package nl.fontys.scope.graphics.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.LightingManager;
import nl.fontys.scope.object.GameObject;

public class EnergyRenderer extends ModelRenderer {

    private float scale = 1f;

    private long tick = 1;

    private Map<String, EnergyData> map = new HashMap<String, EnergyData>();

    private class EnergyData {
        float scale = 1f;
        long tick = 1;
    }

    public EnergyRenderer() {
        super(AssetManager.getModel(Assets.Models.ENERGY));
    }

    @Override
    public ModelInstance getCurrentInstance(GameObject object, LightingManager lightingManager) {
        PointLight light = lightingManager.getPointLightById(object.getId());
        EnergyData data = map.get(object.getId());
        if (light == null) {
            light = new PointLight();
            lightingManager.addPointLight(object.getId(), light);
            data = new EnergyData();
            map.put(object.getId(), data);
        }
        light.set(Color.valueOf("00ff33"), object.getPosition().x, object.getPosition().y, object.getPosition().z, 50f * data.scale);
        data.scale = 8.5f + (float)Math.sin(data.tick / 30f) * 0.5f;
        object.setScale(data.scale);
        data.tick++;
        return super.getCurrentInstance(object, lightingManager);
    }

    @Override
    public boolean hasLighting() {
        return false;
    }
}
