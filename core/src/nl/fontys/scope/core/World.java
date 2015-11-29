package nl.fontys.scope.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.controller.GameObjectController;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.graphics.LightingManager;
import nl.fontys.scope.graphics.RenderManager;
import nl.fontys.scope.graphics.renderer.EnergyRenderer;
import nl.fontys.scope.graphics.renderer.ModelRenderer;
import nl.fontys.scope.graphics.renderer.ShipRenderer;
import nl.fontys.scope.object.GameObject;

/**
 * World which provides game object management
 */
public class World {

    private PerspectiveCamera camera;

    private RenderManager renderManager;

    private Physics physics;

    private LightingManager lightingManager;

    Pool<nl.fontys.scope.object.GameObject> gameObjectPool = new Pool(256) {
        @Override
        protected nl.fontys.scope.object.GameObject newObject() {
            return new nl.fontys.scope.object.GameObject();
        }
    };

    private Map<String, nl.fontys.scope.object.GameObject> objects = new HashMap<String, nl.fontys.scope.object.GameObject>();

    Events events = Events.getInstance();

    private Map<String, List<GameObjectController>> controllers = new HashMap<String, List<GameObjectController> >();

    private List<GameObjectController> globalControllers = new ArrayList<GameObjectController>();

    public World() {
        physics = new Physics();
        lightingManager = new LightingManager();
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(1f, 1f, 1f);
        camera.near = 0.2f;
        camera.far = 30000f;
        camera.update();
        renderManager = new RenderManager(lightingManager);
        renderManager.register(nl.fontys.scope.object.GameObjectType.SHIP, new ShipRenderer());
        renderManager.register(nl.fontys.scope.object.GameObjectType.RING, new ModelRenderer(AssetManager.getModel(Assets.Models.RING)));
        renderManager.register(nl.fontys.scope.object.GameObjectType.PLANET, new ModelRenderer(AssetManager.getModel(Assets.Models.PLANET)));
        renderManager.register(nl.fontys.scope.object.GameObjectType.ENERGY, new EnergyRenderer());

        lightingManager.setAmbientLight(0.2f, 0.1f, 0.7f, 1f);
        lightingManager.addDirectionalLight(UUID.randomUUID().toString(), new DirectionalLight().set(0.0f, 0.6f, 1.0f, 0f, -0.2f, -1f));
        lightingManager.addDirectionalLight(UUID.randomUUID().toString(), new DirectionalLight().set(0.4f, 0.0f, 0.9f, 0f, -0.2f, 1.0f));
    }

    public void addController(nl.fontys.scope.object.GameObject gameObject, GameObjectController controller) {
        if (objects.containsKey(gameObject.getId())) {
            if (!controllers.containsKey(gameObject.getId())) {
                controllers.put(gameObject.getId(), new ArrayList<GameObjectController>());
            }
            controllers.get(gameObject.getId()).add(controller);
        }
    }

    public void addController(GameObjectController controller) {
        globalControllers.add(controller);
    }

    public void dispose() {
        controllers.clear();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    public nl.fontys.scope.object.GameObject createGameObject() {
        nl.fontys.scope.object.GameObject object = gameObjectPool.obtain();
        objects.put(object.getId(), object);
        return object;
    }

    public void remove(nl.fontys.scope.object.GameObject gameObject) {
        if (objects.remove(gameObject) != null) {
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
            renderManager.render(object, camera);
        }
    }
}
