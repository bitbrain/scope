package nl.fontys.scope.net.handlers.requests;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.handlers.AbstractGameInstanceHandler;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;

public class AddObjectHandler extends AbstractGameInstanceHandler {

    public AddObjectHandler(GameInstanceManager gameInstanceManager) {
        super(gameInstanceManager);
    }

    @Override
    public void handle(Connection connection, Object object) {
        String gameId = ((Requests.AddObject)object).getGameId();
        String clientId = ((Requests.AddObject)object).getClientId();
        GameObject gameObject = ((Requests.AddObject)object).getGameObject();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
            instance.validateClientId(clientId);
            instance.sendToAllTCP(new Responses.GameObjectAdded(gameId, clientId, gameObject), clientId);
        } catch (GameServerException e) {
            e.printStackTrace();
            connection.close();
        }
    }

    @Override
    public Class<?> getType() {
        return Requests.AddObject.class;
    }
}
