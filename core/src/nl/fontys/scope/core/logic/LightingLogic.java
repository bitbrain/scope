package nl.fontys.scope.core.logic;

import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.graphics.LightingManager;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.util.Colors;

public class LightingLogic extends MoveableGameObjectLogic {

    private PointLight light;

    private LightingManager lightingManager;

    private int strength = 4;

    public LightingLogic(LightingManager lightingManager) {
        this.lightingManager = lightingManager;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    protected void onRemove(GameObject target) {
        lightingManager.removePointLight(target.getId());
    }

    @Override
    protected void onUpdate(GameObject object, Vector3 offset, float delta) {
        if (light == null) {
            light = new PointLight().set(Colors.SECONDARY, object.getPosition().cpy(), strength);
            lightingManager.addPointLight(object.getId(), light);
        }
        light.setPosition(offset.add(object.getPosition()));
    }
}
