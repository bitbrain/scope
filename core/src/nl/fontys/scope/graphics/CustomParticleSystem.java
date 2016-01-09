package nl.fontys.scope.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

import nl.fontys.scope.Config;

/**Singleton class which manages the particle effects.
 * It's a utility class to ease particle batches management and particle effects update. 
 * @author inferno*/
public final class CustomParticleSystem implements RenderableProvider{
    private static CustomParticleSystem instance;

    public interface ParticleSystemListener {
        void onComplete(ParticleEffect effect);
    }

    public static CustomParticleSystem get(){
        if(instance == null)
            instance = new CustomParticleSystem();
        return instance;
    }

    private Array<ParticleBatch<?>> batches;
    private Array<ParticleEffect> effects;
    private List<ParticleSystemListener> listeners;

    private CustomParticleSystem() {
        batches = new Array<ParticleBatch<?>>();
        effects = new Array<ParticleEffect>();
        listeners = new ArrayList<ParticleSystemListener>();
    }

    public void addListener(ParticleSystemListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ParticleSystemListener listener) {
        this.listeners.remove(listener);
    }

    public void add(ParticleBatch<?> batch){
        batches.add(batch);
    }

    public void add(ParticleEffect effect){
        effects.add(effect);
        if (!Config.HIGH_QUALITY) {
            Emitter emitter = effect.getControllers().first().emitter;
            if (emitter instanceof RegularEmitter) {
                RegularEmitter reg = (RegularEmitter) emitter;
                final int REDUCTION_FACTOR =5;
                reg.setMinParticleCount(reg.getMinParticleCount() / REDUCTION_FACTOR);
                reg.setMaxParticleCount(reg.getMaxParticleCount() / REDUCTION_FACTOR);
            }
        }
    }

    public void remove(ParticleEffect effect){
        effects.removeValue(effect, true);
    }

    /**Removes all the effects added to the system */
    public void removeAll () {
        effects.clear();
    }

    /** Updates the simulation of all effects */
    public void update(){
        for(ParticleEffect effect : effects){
            effect.update();
        }
    }

    public void updateAndDraw(){
        for(ParticleEffect effect : effects){
            effect.update();
            effect.draw();
            Emitter emitter = effect.getControllers().first().emitter;
            if (emitter instanceof RegularEmitter) {
                RegularEmitter reg = (RegularEmitter) emitter;
                if (!reg.isContinuous() && reg.isComplete()) {
                    for (ParticleSystemListener l : listeners) {
                        l.onComplete(effect);
                    }
                }
            }
        }
    }

    /** Must be called one time per frame before any particle effect drawing operation will occur. */
    public void begin(){
        for(ParticleBatch<?> batch : batches)
            batch.begin();
    }

    /** Draws all the particle effects.
     * Call {@link #begin()} before this method and {@link #end()} after.*/
    public void draw(){
        for(ParticleEffect effect : effects){
            effect.draw();
        }
    }

    /** Must be called one time per frame at the end of all drawing operations. */
    public void end(){
        for(ParticleBatch<?> batch : batches)
            batch.end();
    }

    @Override
    public void getRenderables (Array<Renderable> renderables, Pool<Renderable> pool) {
        for(ParticleBatch<?> batch : batches)
            batch.getRenderables(renderables, pool);
    }

    public Array<ParticleBatch<?>> getBatches () {
        return batches;
    }
}