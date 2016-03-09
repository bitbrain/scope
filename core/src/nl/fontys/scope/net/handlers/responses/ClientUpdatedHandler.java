package nl.fontys.scope.net.handlers.responses;

import com.esotericsoftware.kryonet.Connection;

import java.util.logging.Logger;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.World;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.handlers.AbstractGameClientHandler;
import nl.fontys.scope.net.server.Responses;

public class ClientUpdatedHandler extends AbstractGameClientHandler {

    private static final Logger LOGGER = Logger.getLogger(ClientUpdatedHandler.class.getName());

    private PlayerManager playerManager;

    public ClientUpdatedHandler(GameClient client, World world, PlayerManager playerManager) {
        super(client, world);
        this.playerManager = playerManager;
    }

    @Override
    public void handle(Connection connection, Object object) {
        Responses.ClientUpdated updated = (Responses.ClientUpdated)object;
        Player player = playerManager.getPlayerById(updated.getClientId());
        if (player != null) {
            // TODO: update player here
        } else {
            LOGGER.warning("Could not update player with ID '" + updated.getClientId() + "': player does not exist!");
        }
    }

    @Override
    public Class<?> getType() {
        return Responses.ClientUpdated.class;
    }
}
