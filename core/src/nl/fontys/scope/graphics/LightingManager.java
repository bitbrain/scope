package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages dynamic lighting
 */
public class LightingManager {

    private Environment environment;

    private Map<String, SpotLight> spotLights = new HashMap<String, SpotLight>();

    private Map<String, PointLight> pointLights = new HashMap<String, PointLight>();

    private Map<String, DirectionalLight> dirLights = new HashMap<String, DirectionalLight>();


    public LightingManager() {
        this.environment = new Environment();
    }

    public void addSpotLight(String id, SpotLight spotlight) {
        if (this.spotLights.containsKey(id)) {
            this.environment.remove(this.spotLights.get(id));
        }
        this.spotLights.put(id, spotlight);
        this.environment.add(spotlight);
    }

    public SpotLight getSpotLightById(String id) {
        return spotLights.get(id);
    }

    public void removeSpotLight(String id) {
        SpotLight light = spotLights.get(id);
        environment.remove(light);
        spotLights.remove(id);
    }

    public void addPointLight(String id, PointLight pointLight) {
        if (this.pointLights.containsKey(id)) {
            this.environment.remove(this.pointLights.get(id));
        }
        this.pointLights.put(id, pointLight);
        this.environment.add(pointLight);
    }

    public PointLight getPointLightById(String id) {
        return pointLights.get(id);
    }

    public void removePointLight(String id) {
        PointLight light = pointLights.get(id);
        environment.remove(light);
        pointLights.remove(id);
    }

    public void addDirectionalLight(String id, DirectionalLight dirlight) {
        if (this.dirLights.containsKey(id)) {
            this.environment.remove(this.dirLights.get(id));
        }
        this.dirLights.put(id, dirlight);
        this.environment.add(dirlight);
    }

    public DirectionalLight getDirectionalLightById(String id) {
        return dirLights.get(id);
    }

    public void removeDirectionalLight(String id) {
        DirectionalLight light = dirLights.get(id);
        environment.remove(light);
        dirLights.remove(id);
    }

    public void setAmbientLight(float r, float g, float b, float a) {
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, r, g, b, a));
    }

    public Environment getEnvironment() {
        return this.environment;
    }


}
