package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;

public class LeaveGameHandler implements RequestHandler {

    @Override
    public void handle(Connection connection, Object object, GameInstanceManager gameInstanceManager) {
        String gameId = ((Requests.LeaveGame)object).getGameId();
        String clientId = ((Requests.LeaveGame)object).getClientId();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
            instance.validateClientId(clientId);
            instance.removeClient(clientId);
            instance.sendToAllTCP(new Responses.ClientLeft(gameId, clientId));
            if (instance.getClientSize() < 1) {
                instance.sendToAllTCP(new Responses.GameOver(gameId, ""));
                gameInstanceManager.close(gameId);
            }
        } catch (GameServerException e) {
            e.printStackTrace();
            connection.close();
        }
    }

    @Override
    public Class<?> getType() {
        return Requests.LeaveGame.class;
    }
}
