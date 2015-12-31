package nl.fontys.scope.core.controller;

import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;

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

    public ParticleEffectController(Assets.ParticleEffects effect) {
        this.type = effect;
        events.register(this);
    }

    @Override
    public void update(GameObject object, float delta) {
        if (effect == null) {
            effect = particleManager.create(object.getPosition(), type);
            target = object;
        }
        effect.translate(object.getPosition());
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
