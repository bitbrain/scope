package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.ConnectionManager;
import nl.fontys.scope.net.server.GameInstanceManager;

public class CreateGameHandler implements RequestHandler {

    @Override
    public void handle(Connection connection, Object object, ConnectionManager connectionManager, GameInstanceManager gameInstanceManager) {
        String gameId = ((Requests.CreateGame)object).getGameId();

    }
}
