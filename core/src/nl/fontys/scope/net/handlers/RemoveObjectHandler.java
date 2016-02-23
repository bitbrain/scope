package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;

public class RemoveObjectHandler implements RequestHandler {

    @Override
    public void handle(Connection connection, Object object, GameInstanceManager gameInstanceManager) {
        String gameId = ((Requests.RemoveObject)object).getGameId();
        String clientId = ((Requests.RemoveObject)object).getClientId();
        String gameObjectId = ((Requests.RemoveObject)object).getGameObjectId();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
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
