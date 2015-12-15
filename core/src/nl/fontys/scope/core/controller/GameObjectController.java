package nl.fontys.scope.core.controller;

import nl.fontys.scope.object.GameObject;

public interface GameObjectController {

    void update(GameObject object, float delta);

    void update(GameObject object, GameObject other, float delta);
}
