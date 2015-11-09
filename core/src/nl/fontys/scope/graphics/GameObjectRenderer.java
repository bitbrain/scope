package nl.fontys.scope.graphics;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import nl.fontys.scope.core.GameObject;

/**
 * Specifies how to render a certain object
 */
public interface GameObjectRenderer {

    ModelInstance getCurrentInstance(GameObject object);
}
