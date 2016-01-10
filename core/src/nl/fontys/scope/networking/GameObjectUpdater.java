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
        if (ownObject != null) {
            ownObject.set(object);
        }
    }

    public void createGameObject(GameObject object){
        if (world.getObjectById(object.getId()) != null) {
            GameObject ownObject = world.createGameObject();
            ownObject.set(object);
        }
    }

    public void removeGameObject(GameObject object){
        world.remove(world.getObjectById(object.getId()));
    }
}
