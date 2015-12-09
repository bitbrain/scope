package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;

class ModelInstanceProvider {

    protected ModelInstance instance;

    private boolean lighting;

    public ModelInstanceProvider(Model model) {
        this(model, true);
    }

    public ModelInstanceProvider(Model model, boolean lighting) {
        this.instance = new ModelInstance(model);
        Material material = this.instance.materials.get(0);
        if (!material.has(BlendingAttribute.Type)) {
            BlendingAttribute blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            material.set(blendingAttribute);
        }
        this.lighting = lighting;
    }

    public ModelInstance get(GameObject object) {
        Vector3 pos = object.getPosition();
        Quaternion ori = object.getOrientation();
        float scale = object.getScale();
        BlendingAttribute attribute = (BlendingAttribute) instance.materials.get(0).get(BlendingAttribute.Type);
        instance.transform.set(pos.x, pos.y, pos.z, ori.x, ori.y, ori.z, ori.w);
        instance.transform.scale(scale, scale, scale);
        attribute.opacity = object.getColor().a;
        return instance;
    }

    public boolean hasLighting() {
        return lighting;
    }


}
