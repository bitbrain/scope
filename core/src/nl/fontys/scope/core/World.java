package nl.fontys.scope.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import nl.fontys.scope.core.controller.GameObjectController;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.graphics.LightingManager;
import nl.fontys.scope.graphics.ModelInstanceService;
import nl.fontys.scope.graphics.ParticleManager;
import nl.fontys.scope.graphics.RenderManager;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.util.Colors;

/**
 * World which provides game object management
 */
public class World {

    private PerspectiveCamera camera;

    private RenderManager renderManager;

    private Physics physics;

    private LightingManager lightingManager;

    private ParticleManager particleManager = ParticleManager.getInstance();

    Pool<nl.fontys.scope.object.GameObject> gameObjectPool = new Pool(256) {
        @Override
        protected nl.fontys.scope.object.GameObject newObject() {
            return new nl.fontys.scope.object.GameObject();
        }
    };

    private Map<String, nl.fontys.scope.object.GameObject> objects = new ConcurrentHashMap<String, GameObject>();

    Events events = Events.getInstance();

    private Map<String, List<GameObjectController>> controllers = new HashMap<String, List<GameObjectController> >();

    private List<GameObjectController> globalControllers = new ArrayList<GameObjectController>();

    private ModelInstanceService modelInstanceService;

    private CollisionDetector collisionDetector;

    private Arena.ArenaBoundRestrictor restrictor;

    public World() {
        physics = new Physics();
        lightingManager = new LightingManager();
        modelInstanceService = new ModelInstanceService();
        collisionDetector = new CollisionDetector(modelInstanceService);
        camera = new PerspectiveCamera(80f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(1f, 1f, 1f);
        camera.near = 0.2f;
        camera.far = 30000f;

        renderManager = new RenderManager(lightingManager, modelInstanceService);

        lightingManager.setAmbientLight(0.3f, 0.2f, 0.6f, 1f);
        lightingManager.addPointLight(UUID.randomUUID().toString(), new PointLight().set(Colors.PRIMARY, 0f, 0f, 0f, 1800f));
    }

    public LightingManager getLightingManager() {
        return lightingManager;
    }

    public void setRestrictor(Arena.ArenaBoundRestrictor restrictor) {
        this.restrictor = restrictor;
    }

    public void addController(nl.fontys.scope.object.GameObject gameObject, GameObjectController controller) {
        if (objects.containsKey(gameObject.getId())) {
            if (!controllers.containsKey(gameObject.getId())) {
                controllers.put(gameObject.getId(), new ArrayList<GameObjectController>());
            }
            controllers.get(gameObject.getId()).add(controller);
        }
    }

    public GameObject getObjectById(String id) {
        return objects.get(id);
    }

    public void addController(GameObjectController controller) {
        globalControllers.add(controller);
    }

    public void dispose() {
        controllers.clear();
        collisionDetector.dispose();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    public nl.fontys.scope.object.GameObject createGameObject() {
        nl.fontys.scope.object.GameObject object = gameObjectPool.obtain();
        object.reset();

        objects.put(object.getId(), object);
        events.fire(EventType.OBJECT_CREATED, object);
        return object;
    }

    public void remove(nl.fontys.scope.object.GameObject gameObject) {
        if (objects.remove(gameObject.getId()) != null) {
            controllers.remove(gameObject.getId());
            events.fire(EventType.OBJECT_REMOVED, gameObject);
        }
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public void updateAndRender(float delta) {
        camera.update();
        renderManager.background(camera);
        for (GameObject object : objects.values()) {
            // Local controllers
            List<GameObjectController> c = controllers.get(object.getId());
            if (c != null) {
                for (GameObjectController cObject : c) {
                    cObject.update(object, delta);
                }
            }
            // Global controllers
            for (GameObjectController globalController : globalControllers) {
                globalController.update(object, delta);
            }
            physics.apply(object, delta);
            for (GameObject other : objects.values()) {
                if (!object.getId().equals(other.getId())) {
                    if (c != null) {
                        for (GameObjectController cObject : c) {
                            cObject.update(object, other, delta);
                        }
                    }
                    collisionDetector.detect(object, other);
                }
            }
            if (restrictor != null) {
                restrictor.restrict(this, object);
            }
            renderManager.render(object, camera);
        }
        renderManager.particles(camera);
    }
}
