package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;

public final class ParticleManager {

    private class ParticleEffectPool extends Pool<ParticleEffect> implements CustomParticleSystem.ParticleSystemListener {

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

    private CustomParticleSystem system = CustomParticleSystem.get();

    private PointSpriteParticleBatch pointBatch = new PointSpriteParticleBatch();

    private BillboardParticleBatch billboardBatch = new BillboardParticleBatch();

    private Map<Assets.ParticleEffects, ParticleEffectPool> poolMap = new HashMap<Assets.ParticleEffects, ParticleEffectPool>();

    static {
        INSTANCE = new ParticleManager();
    }

    private ParticleManager() {
        pointBatch.setSorter(new ParticleSorter.Distance());
        billboardBatch.setSorter(new ParticleSorter.Distance());
        system.add(pointBatch);
        system.add(billboardBatch);
    }

    public CustomParticleSystem getSystem() {
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
        effect.translate(position.cpy());
        effect.init();
        effect.start();
        system.add(effect);
        return effect;
    }

    public void remove(ParticleEffect effect) {
        remove(effect, false);
    }

    public void remove(ParticleEffect effect, boolean force) {
        if (force) {
            system.remove(effect);
        } else {
            for (ParticleController c :effect.getControllers()) {
                Emitter emitter = c.emitter;
                if (emitter instanceof RegularEmitter) {
                    ((RegularEmitter)emitter).setContinuous(false);
                }
            }
        }
    }

    public void clear() {
        system.removeAll();
    }

    public void setCamera(Camera camera) {
        pointBatch.setCamera(camera);
        billboardBatch.setCamera(camera);
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
