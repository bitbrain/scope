package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;

public final class ParticleManager {

    private class ParticleEffectPool extends Pool<ParticleEffect> implements ParticleSystem.ParticleSystemListener {

        private ParticleEffect sourceEffect;

        public ParticleEffectPool(ParticleEffect sourceEffect) {
            this.sourceEffect = sourceEffect;
        }

        @Override
        public void free(ParticleEffect pfx) {
            pfx.reset();
            super.free(pfx);
        }

        @Override
        protected ParticleEffect newObject() {
            return sourceEffect.copy();
        }

        @Override
        public void onComplete(ParticleEffect effect) {
            free(effect);
        }
    }

    private static final ParticleManager INSTANCE;

    private ParticleSystem system = ParticleSystem.get();

    private BillboardParticleBatch batch = new BillboardParticleBatch();

    private Map<Assets.ParticleEffects, ParticleEffectPool> poolMap = new HashMap<Assets.ParticleEffects, ParticleEffectPool>();

    static {
        INSTANCE = new ParticleManager();
    }

    private ParticleManager() {
        system.add(batch);
    }

    public ParticleSystem getSystem() {
        return system;
    }

    public static ParticleManager getInstance() {
        return INSTANCE;
    }

    public void render(ModelBatch batch) {
        system.update();
        system.begin();
        system.draw();
        system.end();
        batch.render(system);
    }

    public ParticleEffect create(Vector3 position, Assets.ParticleEffects effectType) {
        ParticleEffectPool pool = preparePool(effectType);
        ParticleEffect effect = pool.obtain();
        effect.translate(position);
        effect.init();
        effect.start();
        system.add(effect);
        return effect;
    }

    public void setCamera(Camera camera) {
        batch.setCamera(camera);
    }

    private ParticleEffectPool preparePool(Assets.ParticleEffects effectType) {
        ParticleEffectPool pool = poolMap.get(effectType);
        if (pool == null) {
            pool = new ParticleEffectPool(AssetManager.getParticleEffect(effectType));
            poolMap.put(effectType, pool);
            system.addListener(pool);
        }
        return pool;
    }
}
