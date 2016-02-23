package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;

public class JoinGameHandler implements RequestHandler {

    @Override
    public void handle(Connection connection, Object object, GameInstanceManager gameInstanceManager) {
        String gameId = ((Requests.JoinGame)object).getGameId();
        String clientId = ((Requests.JoinGame)object).getClientId();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
            instance.addClient(gameId, connection);
            instance.sendToAllTCP(new Responses.ClientJoined(gameId, clientId));
            if (instance.isGameFull()) {
                instance.sendToAllTCP(new Responses.GameReady(gameId));
            }
        } catch (GameServerException e) {
            e.printStackTrace();
            connection.close();
        }
    }

    @Override
    public Class<?> getType() {
        return Requests.JoinGame.class;
    }
}
