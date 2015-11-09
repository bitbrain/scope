package nl.fontys.scope.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.graphics.RenderManager;
import nl.fontys.scope.graphics.renderer.ModelRenderer;

/**
 * World which provides game object management
 */
public class World {

    private PerspectiveCamera camera;

    private RenderManager renderManager;

    private CameraInputController camController;

    Pool<GameObject> gameObjectPool = new Pool(256) {
        @Override
        protected GameObject newObject() {
            return new GameObject();
        }
    };

    private Map<String, GameObject> objects = new HashMap<String, GameObject>();

    Events events = Events.getInstance();

    public World() {
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(1f, 1f, 1f);
        camera.near = 0.2f;
        camera.far = 30000f;
        camera.update();
        renderManager = new RenderManager();
        renderManager.register(GameObjectType.SHIP, new ModelRenderer(AssetManager.getModel(Assets.Models.CRUISER)));

        camController = new CameraInputController(camera) {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }
        };
        Gdx.input.setInputProcessor(camController);
    }

    public void dispose() {
        // noOp
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    public GameObject createGameObject() {
        GameObject object = gameObjectPool.obtain();
        objects.put(object.getId(), object);
        return object;
    }

    public void remove(GameObject gameObject) {
        if (objects.remove(gameObject) != null) {
            events.fire(EventType.OBJECT_REMOVED, gameObject);
        }
    }

    public void updateAndRender(float delta) {
        camController.update();
        camera.update();
        renderManager.background(camera);
        for (GameObject object : objects.values()) {
            renderManager.render(object, camera);
        }
    }
}
