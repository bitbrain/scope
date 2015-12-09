package nl.fontys.scope.graphics.renderer;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.graphics.LightingManager;

public class DefaultModelInstanceProvider implements ModelInstanceProvider {

    protected ModelInstance instance;

    private boolean lighting;

    public DefaultModelInstanceProvider(Model model) {
        this(model, true);
    }

    public DefaultModelInstanceProvider(Model model, boolean lighting) {
        this.instance = new ModelInstance(model);
        this.lighting = lighting;
    }

    @Override
    public ModelInstance get(GameObject object) {
        Vector3 pos = object.getPosition();
        Quaternion ori = object.getOrientation();
        float scale = object.getScale();
        instance.transform.set(pos.x, pos.y, pos.z, ori.x, ori.y, ori.z, ori.w);
        instance.transform.scale(scale, scale, scale);
        return instance;
    }

    @Override
    public boolean hasLighting() {
        return lighting;
    }


}
