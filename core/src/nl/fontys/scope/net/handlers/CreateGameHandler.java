package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.ConnectionManager;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;

public class CreateGameHandler implements RequestHandler {

    @Override
    public void handle(Connection connection, Object object, ConnectionManager connectionManager, GameInstanceManager gameInstanceManager) {
        String gameId = ((Requests.CreateGame)object).getGameId();
        try {
            GameInstance instance = gameInstanceManager.create(gameId);
            String playerId = instance.addPlayer(connection);
            connection.sendTCP(new Responses.GameCreated(gameId, playerId));
        } catch (GameServerException e) {
            e.printStackTrace();
            connection.close();
        }
    }
}
