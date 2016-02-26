package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;

public class WinGameHandler extends AbstractGameInstanceHandler {

    public WinGameHandler(GameInstanceManager gameInstanceManager) {
        super(gameInstanceManager);
    }

    @Override
    public void handle(Connection connection, Object object) {
        String gameId = ((Requests.WinGame)object).getGameId();
        String clientId = ((Requests.WinGame)object).getClientId();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
            instance.validateClientId(clientId);
            instance.sendToAllTCP(new Responses.GameOver(gameId, clientId));
        } catch (GameServerException e) {
            e.printStackTrace();
            connection.close();
        }
    }

    @Override
    public Class<?> getType() {
        return Requests.WinGame.class;
    }
}
