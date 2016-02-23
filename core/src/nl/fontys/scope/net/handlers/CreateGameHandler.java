package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;

public class CreateGameHandler implements RequestHandler {

    @Override
    public void handle(Connection connection, Object object, GameInstanceManager gameInstanceManager) {
        String gameId = ((Requests.CreateGame)object).getGameId();
        String clientId = ((Requests.CreateGame)object).getClientId();
        try {
            GameInstance instance = gameInstanceManager.create(gameId);
            instance.addClient(clientId, connection);
            connection.sendTCP(new Responses.GameCreated(gameId, clientId));
        } catch (GameServerException e) {
            e.printStackTrace();
            connection.close();
        }
    }

    @Override
    public Class<?> getType() {
        return Requests.CreateGame.class;
    }
}
