package nl.fontys.scope.net.handlers.requests;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.handlers.AbstractGameInstanceHandler;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;

public class JoinGameHandler extends AbstractGameInstanceHandler {

    public JoinGameHandler(GameInstanceManager gameInstanceManager) {
        super(gameInstanceManager);
    }

    @Override
    public void handle(Connection connection, Object object) {
        Requests.JoinGame join = (Requests.JoinGame)object;
        String gameId = join.getGameId();
        String clientId = join.getClientId();
        String gameObjectId = join.getGameObjectId();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
            instance.addClient(gameId, connection);
            instance.sendToAllTCP(new Responses.ClientJoined(gameId, clientId, gameObjectId));
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
