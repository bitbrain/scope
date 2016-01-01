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

public class ParticleEffectController extends DynamicGameObjectController {

    private ParticleManager particleManager = ParticleManager.getInstance();

    private ParticleEffect effect;

    private Assets.ParticleEffects type;

    private Matrix4 trans = new Matrix4();

    public ParticleEffectController(Assets.ParticleEffects effect) {
        this.type = effect;
    }

    @Override
    protected void onUpdate(GameObject object, Vector3 localPos, float delta) {
        if (effect == null) {
            effect = particleManager.create(object.getPosition(), type);
        }
        trans = trans.toNormalMatrix();
        trans.translate(localPos.add(object.getPosition()));
        effect.setTransform(trans);
    }

    @Override
    protected void onRemove(GameObject target) {
        particleManager.remove(effect);
    }
}
