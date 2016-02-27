package nl.fontys.scope.net.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameInstanceManager {

    private Map<String, GameInstance> instances;

    public GameInstanceManager() {
        instances = new HashMap<String, GameInstance>();
    }

    public GameInstance create(String name) throws GameServerException {
        validateName(name);
        if (!instances.containsKey(name)) {
            GameInstance instance = new GameInstance(name);
            instances.put(name, instance);
            return instance;
        } else {
            throw new GameServerException("Could not create game '" + name + "'. Game already exists!");
        }
    }

    public Collection<GameInstance> getCurrentInstances() {
        return instances.values();
    }

    public GameInstance get(String name) throws GameServerException {
        if (instances.containsKey(name)) {
            return instances.get(name);
        } else {
            throw new GameServerException("Could not resolve game '" + name + "' because it does not exist.");
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
