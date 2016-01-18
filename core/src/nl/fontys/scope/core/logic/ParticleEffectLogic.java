package nl.fontys.scope.core.logic;

import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.ParticleManager;
import nl.fontys.scope.object.GameObject;

public class ParticleEffectLogic extends MoveableGameObjectLogic {

    private ParticleManager particleManager = ParticleManager.getInstance();

    private ParticleEffect effect;

    private Assets.ParticleEffects type;

    private Matrix4 trans = new Matrix4();

    public ParticleEffectLogic(Assets.ParticleEffects effect) {
        this.type = effect;
    }

    @Override
    protected void onUpdate(GameObject object, Vector3 offset, float delta) {
        if (effect == null) {
            effect = particleManager.create(object.getPosition(), type);
        }
        trans.set(offset.add(object.getPosition()), object.getOrientation());
        effect.setTransform(trans);
    }

    @Override
    protected void onRemove(GameObject target) {
        particleManager.remove(effect);
    }
}
