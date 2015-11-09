package nl.fontys.scope.graphics.renderer;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.core.GameObject;
import nl.fontys.scope.graphics.GameObjectRenderer;

public class ModelRenderer implements GameObjectRenderer {

    private ModelInstance instance;

    public ModelRenderer(Model model) {
        this.instance = new ModelInstance(model);
    }

    @Override
    public ModelInstance getCurrentInstance(GameObject object) {
        Vector3 pos = object.getPosition();
        Quaternion ori = object.getOrientation();
        float scale = object.getScale();
        instance.transform.set(pos.x, pos.y, pos.z, ori.x, ori.y, ori.z, ori.w);
        instance.transform.scale(scale, scale, scale);
        return instance;
    }
}
