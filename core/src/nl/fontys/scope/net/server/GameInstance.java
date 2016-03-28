package nl.fontys.scope.net.server;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.Config;

public class GameInstance {

    private String name;

    private Map<String, Connection> connections;

    private Map<Connection, String> clients;

    public GameInstance(String name) {
        this.name = name;
        this.connections = new HashMap<String, Connection>();
        this.clients = new HashMap<Connection, String>();
    }

    public void validateClientId(String id) throws GameServerException {
        if (id == null || id.trim().isEmpty() || !connections.containsKey(id)) {
            throw new GameServerException("Invalid game client id!");
        }
    }

    public void addClient(String id, Connection connection) throws GameServerException {
        if (isGameFull()) {
            throw new GameServerException("Game is full!");
        }
        if (id == null || id.trim().isEmpty()) {
            throw new GameServerException("Invalid game client id!");
        }
        if (connections.containsKey(id)) {
            throw new GameServerException("Client already connected!");
        }
        if (connection.isConnected()) {
            connections.put(id, connection);
            clients.put(connection, id);
        } else {
            throw new GameServerException("Could not create player. Not connected!");
        }
    }

    public String getClientByConnection(Connection connection) {
        return clients.get(connection);
    }

    public void removeClient(String id) throws GameServerException {
        if (id == null || id.trim().isEmpty()) {
            throw new GameServerException("Invalid game client id!");
        }
        if (!connections.containsKey(id)) {
            throw new GameServerException("Invalid game client id!");
        }
        clients.remove(connections.get(id));
        if (connections.get(id).isConnected()) {
            connections.get(id).close();
        }
        connections.remove(id);
    }

    public void close() {
        for (Connection connection : connections.values()) {
            if (connection.isConnected()) {
                connection.sendTCP(new Responses.GameClosed(name));
                connection.close();
            }
        }
        connections.clear();
    }

    public void sendToAllTCP(Object object, String... exclusions) {
        for (Map.Entry<String, Connection> entry : connections.entrySet()) {
            String clientId = entry.getKey();
            Connection connection = entry.getValue();
            if (connection.isConnected() && isNotExcluded(clientId, exclusions)) {
                connection.sendTCP(object);
            }
        }
    }

    public void sendToAllUDP(Object object, String ... exclusions) {
        for (Map.Entry<String, Connection> entry : connections.entrySet()) {
            String clientId = entry.getKey();
            Connection connection = entry.getValue();
            if (connection.isConnected() && isNotExcluded(clientId, exclusions)) {
                connection.sendUDP(object);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getCurrentClientSize() {
        return connections.size();
    }

    public int getMaxClientSize() {
        return Config.MAX_CLIENT_SIZE;
    }

    public boolean isGameFull() {
        return getCurrentClientSize() == Config.MAX_CLIENT_SIZE;
    }

    private boolean isNotExcluded(String clientId, String[] exclusions) {
        boolean notExcluded = true;
        for (String exclusion : exclusions) {
            if (exclusion.equals(clientId)) {
                return false;
            }
        }
        return notExcluded;
    }
}
