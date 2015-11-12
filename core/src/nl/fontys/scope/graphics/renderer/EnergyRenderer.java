package nl.fontys.scope.graphics.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.LightingManager;
import nl.fontys.scope.object.GameObject;

public class EnergyRenderer extends ModelRenderer {

    private float scale = 1f;

    private long tick = 1;

    public EnergyRenderer() {
        super(AssetManager.getModel(Assets.Models.ENERGY));
    }

    @Override
    public ModelInstance getCurrentInstance(GameObject object, LightingManager lightingManager) {
        PointLight light = lightingManager.getPointLightById(object.getId());
        if (light == null) {
            light = new PointLight();
            lightingManager.addPointLight(object.getId(), light);
        }
        light.set(Color.valueOf("00ff00"), object.getPosition().x, object.getPosition().y, object.getPosition().z, 150f * scale);
        instance.materials.get(0).set(new FloatAttribute(FloatAttribute.AlphaTest, 0.4f));
        scale = 2.5f + (float)Math.sin(tick / 30f) * 0.5f;
        object.setScale(scale);
        tick++;
        return super.getCurrentInstance(object, lightingManager);
    }

    @Override
    public boolean hasLighting() {
        return false;
    }
}
