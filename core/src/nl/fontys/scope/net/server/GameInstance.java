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

    public String addPlayer(Connection connection) throws GameServerException {
        if (connection.isConnected()) {
            String playerId = UUID.randomUUID().toString();
            connections.put(playerId, connection);
            return playerId;
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

    public String getName() {
        return name;
    }
}
