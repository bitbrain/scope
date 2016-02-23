package nl.fontys.scope.net.server;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import nl.fontys.scope.core.Player;

public class GameInstance {

    private String name;

    private Map<String, Connection> connections;

    public GameInstance(String name) {
        this.name = name;
        this.connections = new HashMap<String, Connection>();
    }

    public void addClient(String id, Connection connection) throws GameServerException {
        if (id == null || id.trim().isEmpty()) {
            throw new GameServerException("Invalid game client id!");
        }
        if (connections.containsKey(id)) {
            throw new GameServerException("Client already connected!");
        }
        if (connection.isConnected()) {
            connections.put(id, connection);
        } else {
            throw new GameServerException("Could not create player. Not connected!");
        }

    }

    public void close() {
        for (Connection connection : connections.values()) {
            if (connection.isConnected()) {
                connection.close();
            }
        }
    }

    public void sendToAllTCP(Object object) {
        for (Connection connection : connections.values()) {
            if (connection.isConnected()) {
                connection.sendTCP(object);
            }
        }
    }

    public void sendToAllUDP(Object object) {
        for (Connection connection : connections.values()) {
            if (connection.isConnected()) {
                connection.sendUDP(object);
            }
        }
    }

    public String getName() {
        return name;
    }
}
