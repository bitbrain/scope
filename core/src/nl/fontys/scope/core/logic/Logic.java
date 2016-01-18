package nl.fontys.scope.core.logic;

import nl.fontys.scope.object.GameObject;

public interface Logic {

    void update(GameObject object, float delta);

    void update(GameObject object, GameObject other, float delta);
}
