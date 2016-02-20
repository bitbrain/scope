package nl.fontys.scope.net;

import java.util.HashMap;
import java.util.Map;

public class GameInstanceManager {

    private Map<String, GameInstance> instances;

    public GameInstanceManager() {
        instances = new HashMap<String, GameInstance>();
    }

    public void create(String name) throws GameServerException {
        validateName(name);
        if (!instances.containsKey(name)) {
            GameInstance instance = new GameInstance(name);
            instance.init();
            instances.put(name, instance);
        } else {
            throw new GameServerException("Could not create game '" + name + "'. Game already exists!");
        }
    }

    public void close(String name) throws GameServerException {
        validateName(name);
        if (instances.containsKey(name)) {
            GameInstance instance = instances.get(name);
            instance.close();
            instances.remove(instance);
        } else {
            throw new GameServerException("Could not close game '" + name + "' because it does not exist!");
        }
    }

    private void validateName(String name) throws GameServerException {
        if (name.isEmpty()) {
            throw new GameServerException("Game name should not be empty.");
        }
    }
}
