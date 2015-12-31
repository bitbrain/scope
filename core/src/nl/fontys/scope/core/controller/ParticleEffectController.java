package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import net.engio.mbassy.listener.Handler;

import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.graphics.ParticleManager;
import nl.fontys.scope.object.GameObject;

public class ParticleEffectController implements GameObjectController {

    private Events events = Events.getInstance();

    private ParticleManager particleManager = ParticleManager.getInstance();

    private ParticleEffect effect;

    private Assets.ParticleEffects type;

    private GameObject target;

    private Matrix4 trans = new Matrix4();

    private Vector3 offset = new Vector3(), tmp = new Vector3();

    public ParticleEffectController(Assets.ParticleEffects effect) {
        this.type = effect;
        events.register(this);
    }

    public void setOffset(float x, float y, float z) {
        offset.set(x, y, z);
    }

    @Override
    public void update(GameObject object, float delta) {
        if (effect == null) {
            effect = particleManager.create(object.getPosition(), type);
            target = object;
        }
        trans = trans.toNormalMatrix();
        Quaternion orientation = object.getOrientation();
        tmp.set(offset);
        tmp.rotate(Vector3.X, orientation.getAngleAround(Vector3.X));
        tmp.rotate(Vector3.Y, orientation.getAngleAround(Vector3.Y));
        tmp.rotate(Vector3.Z, orientation.getAngleAround(Vector3.Z));
        trans.translate(tmp.add(object.getPosition()));
        effect.setTransform(trans);
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {

    }

    @Handler
    public void onEvent(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.OBJECT_REMOVED)) {
            GameObject object = (GameObject) event.getPrimaryParam();
            if (object.equals(target)) {
                particleManager.remove(effect);
            }
        }
    }
}
