package nl.fontys.scope.net.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstanceManager {

    private Map<String, GameInstance> instances;

    private class ServerListener extends Listener {

        @Override
        public void disconnected(Connection connection) {
            List<GameInstance> emptyInstances = new ArrayList<GameInstance>();
            for (GameInstance instance : instances.values()) {
                String clientId = instance.getClientByConnection(connection);
                try {
                    if (clientId != null) {
                        instance.removeClient(clientId);
                        if (instance.getCurrentClientSize() <= 1) {
                            emptyInstances.add(instance);
                        }
                    }
                } catch (GameServerException e) {
                    e.printStackTrace();
                }
            }
            for (GameInstance emptyInstance : emptyInstances) {
                emptyInstance.close();
                instances.remove(emptyInstance.getName());
            }
        }
    }

    public GameInstanceManager(Server server) {
        instances = new HashMap<String, GameInstance>();
        server.addListener(new ServerListener());
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
            instances.remove(name);
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
