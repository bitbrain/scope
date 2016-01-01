package nl.fontys.scope.core.controller;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import net.engio.mbassy.listener.Handler;

import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;

public abstract class DynamicGameObjectController implements GameObjectController {

    private Events events = Events.getInstance();

    protected GameObject target;

    private Vector3 offset = new Vector3(), tmp = new Vector3();

    public DynamicGameObjectController() {
        events.register(this);
    }

    public void setOffset(float x, float y, float z) {
        offset.set(x, y, z);
    }

    @Handler
    public void onEvent(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.OBJECT_REMOVED)) {
            GameObject object = (GameObject) event.getPrimaryParam();
            if (object.equals(target)) {
                onRemove(target);
            }
        }
    }

    @Override
    public void update(GameObject object, float delta) {
        if (target == null) {
            target = object;
        }
        Quaternion orientation = object.getOrientation();
        tmp.set(offset);
        tmp.rotate(Vector3.X, orientation.getAngleAround(Vector3.X));
        tmp.rotate(Vector3.Y, orientation.getAngleAround(Vector3.Y));
        tmp.rotate(Vector3.Z, orientation.getAngleAround(Vector3.Z));
        onUpdate(object, tmp, delta);
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }

    protected abstract void onRemove(GameObject target);

    protected abstract void onUpdate(GameObject object, Vector3 offset, float delta);
}
