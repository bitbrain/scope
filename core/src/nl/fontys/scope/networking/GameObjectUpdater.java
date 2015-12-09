package nl.fontys.scope.networking;

import nl.fontys.scope.core.World;
import nl.fontys.scope.object.GameObject;

public class GameObjectUpdater {

    private World world;

    public GameObjectUpdater(World world) {
        this.world = world;
    }

    public void updateGameObject(GameObject object) {
        GameObject ownObject = world.getObjectById(object.getId());
        ownObject.set(object);
    }
}
