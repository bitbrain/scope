package nl.fontys.scope.net.handlers.responses;

import com.esotericsoftware.kryonet.Connection;

import java.util.logging.Logger;

import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.World;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.handlers.AbstractGameClientHandler;
import nl.fontys.scope.net.server.Responses;

public class ClientJoinedHandler extends AbstractGameClientHandler {

    private static final Logger LOGGER = Logger.getLogger(ClientJoinedHandler.class.getName());

    private PlayerManager playerManager;

    public ClientJoinedHandler(GameClient client, World world, PlayerManager playerManager) {
        super(client, world);
        this.playerManager = playerManager;
    }

    @Override
    public void handle(Connection connection, Object object) {
        Responses.ClientJoined joined = (Responses.ClientJoined)object;
        if (playerManager.addPlayer(joined.getClientId(), joined.getGameObjectId()) != null) {
            LOGGER.info("Player joined with id '" + joined.getClientId() + "'");
        } else {
            LOGGER.warning("PLayer with id '" + joined.getClientId()  +"' could not join!");
        }
    }

    @Override
    public Class<?> getType() {
        return Responses.ClientJoined.class;
    }
}
