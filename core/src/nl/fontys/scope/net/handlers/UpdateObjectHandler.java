package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;

public class UpdateObjectHandler implements RequestHandler {

    @Override
    public void handle(Connection connection, Object object, GameInstanceManager gameInstanceManager) {
        String gameId = ((Requests.UpdateObject)object).getGameId();
        String clientId = ((Requests.UpdateObject)object).getClientId();
        GameObject gameObject = ((Requests.UpdateObject)object).getGameObject();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
            instance.sendToAllUDP(new Responses.GameObjectUpdated(gameId, clientId, gameObject), clientId);
        } catch (GameServerException e) {
            e.printStackTrace();
            connection.close();
        }
    }

    @Override
    public Class<?> getType() {
        return Requests.UpdateObject.class;
    }
}
