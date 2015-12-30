package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;

public final class ParticleManager {

    private static final ParticleManager INSTANCE;

    private ParticleSystem system = ParticleSystem.get();

    private BillboardParticleBatch batch = new BillboardParticleBatch();

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

    public void setCamera(PerspectiveCamera camera) {
        batch.setCamera(camera);
    }
}
