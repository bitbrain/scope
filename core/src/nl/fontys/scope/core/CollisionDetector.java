package nl.fontys.scope.core;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;

import net.engio.mbassy.listener.Handler;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.graphics.ModelInstanceService;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

public class CollisionDetector {

    private final ModelInstanceService service;

    private Map<String, BoundingBox> boxes = new HashMap<String, BoundingBox>();
    private Map<String, BoundingBox> originals = new HashMap<String, BoundingBox>();

    private Events events = Events.getInstance();

    public CollisionDetector(ModelInstanceService service) {
        this.service = service;
        this.events.register(this);
    }

    public void dispose() {
        this.events.unregister(this);
    }

    @Handler
    public void onObjectRemove(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.OBJECT_REMOVED)) {
            GameObject object = (GameObject) event.getPrimaryParam();
            boxes.remove(object.getId());
            originals.remove(object.getId());
        }
    }

    public void detect(GameObject objectA, GameObject objectB) {
        // Do not calculate bounding boxes for none objects
        if (objectA.getType().equals(GameObjectType.NONE) || objectB.getType().equals(GameObjectType.NONE)) {
            return;
        }
        BoundingBox boxA = getBoundingBox(objectA);
        BoundingBox boxB = getBoundingBox(objectB);
        if (boxA.contains(boxB)) {
            events.fire(EventType.COLLISION_FULL, objectA, objectB);
        } else if (boxA.intersects(boxB)) {
            events.fire(EventType.COLLISION, objectA, objectB);
        }
    }

    private BoundingBox getBoundingBox(GameObject object) {
        BoundingBox box = boxes.get(object.getId());
        BoundingBox original = originals.get(object.getId());
        ModelInstance instance = service.getCurrentInstance(object);
        if (box == null) {
            box = new BoundingBox();
            original = new BoundingBox();
            instance.calculateBoundingBox(original);
            boxes.put(object.getId(), box);
            originals.put(object.getId(), original);
        }
        box.set(original);
        box.min.sub(object.getCollisionScale());
        box.max.add(object.getCollisionScale());
        box.mul(instance.transform);
        return box;
    }
}
