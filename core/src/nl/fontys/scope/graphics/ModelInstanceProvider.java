package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
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
        material.set(ColorAttribute.createDiffuse(Color.WHITE));
        this.lighting = lighting;
    }

    public ModelInstance get(GameObject object) {
        Vector3 pos = object.getPosition();
        Quaternion ori = object.getOrientation();
        float scale = object.getScale();
        BlendingAttribute blending = (BlendingAttribute) instance.materials.get(0).get(BlendingAttribute.Type);
        ColorAttribute color = (ColorAttribute) instance.materials.get(0).get(ColorAttribute.Diffuse);
        instance.transform.set(pos.x, pos.y, pos.z, ori.x, ori.y, ori.z, ori.w);
        instance.transform.scale(scale, scale, scale);
        color.color.set(object.getColor());
        blending.opacity = object.getColor().a;
        return instance;
    }

    public boolean hasLighting() {
        return lighting;
    }


}
