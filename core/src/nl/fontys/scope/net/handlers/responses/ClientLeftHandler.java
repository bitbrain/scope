package nl.fontys.scope.net.handlers.responses;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.World;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.handlers.AbstractGameClientHandler;
import nl.fontys.scope.net.server.Responses;

public class ClientLeftHandler extends AbstractGameClientHandler {

    private PlayerManager playerManager;

    public ClientLeftHandler(GameClient client, World world, PlayerManager playerManager) {
        super(client, world);
        this.playerManager = playerManager;
    }

    @Override
    public void handle(Connection connection, Object object) {
        Responses.ClientLeft left = (Responses.ClientLeft)object;
        this.playerManager.removePlayer(left.getClientId());

    }

    @Override
    public Class<?> getType() {
        return Responses.ClientLeft.class;
    }
}
