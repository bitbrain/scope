package nl.fontys.scope.net.handlers.requests;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.handlers.AbstractGameInstanceHandler;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;

public class RemoveObjectHandler extends AbstractGameInstanceHandler {

    public RemoveObjectHandler(GameInstanceManager gameInstanceManager) {
        super(gameInstanceManager);
    }

    @Override
    public void handle(Connection connection, Object object) {
        String gameId = ((Requests.RemoveObject)object).getGameId();
        String clientId = ((Requests.RemoveObject)object).getClientId();
        String gameObjectId = ((Requests.RemoveObject)object).getGameObjectId();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
            instance.validateClientId(clientId);
            instance.sendToAllTCP(new Responses.GameObjectRemoved(gameId, clientId, gameObjectId), clientId);
        } catch (GameServerException e) {
            e.printStackTrace();
            connection.close();
        }
    }

    @Override
    public Class<?> getType() {
        return Requests.RemoveObject.class;
    }
}
