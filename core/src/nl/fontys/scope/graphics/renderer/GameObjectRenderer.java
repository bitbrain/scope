package nl.fontys.scope.graphics.renderer;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.graphics.LightingManager;

/**
 * Specifies how to render a certain object
 */
public interface GameObjectRenderer {

    ModelInstance getCurrentInstance(GameObject object, LightingManager lightingManager);
}
