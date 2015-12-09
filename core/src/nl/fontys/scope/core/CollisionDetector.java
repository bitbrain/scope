package nl.fontys.scope.core;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.graphics.ModelInstanceService;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

public class CollisionDetector {

    private final ModelInstanceService service;

    private Map<String, BoundingBox> boxes = new HashMap<String, BoundingBox>();
    private Map<String, BoundingBox> originals = new HashMap<String, BoundingBox>();

    public CollisionDetector(ModelInstanceService service) {
        this.service = service;
    }

    public void detect(GameObject target, Collection<GameObject> others) {
        for (GameObject object : others) {
            if (!target.getId().equals(object.getId())) {
                BoundingBox boxA = getBoundingBox(target);
                BoundingBox boxB = getBoundingBox(object);
                if (boxA.intersects(boxB)) {
                    // TODO handle detection
                }
            }
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
        box.mul(instance.transform);
        return box;
    }
}
