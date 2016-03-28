package nl.fontys.scope.net.handlers.requests;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.net.server.GameServerException;
import nl.fontys.scope.net.server.Responses;

public class LeaveGameHandler extends nl.fontys.scope.net.handlers.AbstractGameInstanceHandler {

    public LeaveGameHandler(GameInstanceManager gameInstanceManager) {
        super(gameInstanceManager);
    }

    @Override
    public void handle(Connection connection, Object object) {
        String gameId = ((Requests.LeaveGame)object).getGameId();
        String clientId = ((Requests.LeaveGame)object).getClientId();
        try {
            GameInstance instance = gameInstanceManager.get(gameId);
            instance.validateClientId(clientId);
            instance.removeClient(clientId);
            instance.sendToAllTCP(new Responses.ClientLeft(gameId, clientId, instance.getCurrentClientSize(), instance.getMaxClientSize()));
            boolean waitingGameEmpty = instance.getCurrentClientSize() < 1;
            boolean runningGameEmpty = instance.getGameState().equals(GameInstance.GameState.RUNNING) && instance.getCurrentClientSize() < 2;
            if (waitingGameEmpty || runningGameEmpty){
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
