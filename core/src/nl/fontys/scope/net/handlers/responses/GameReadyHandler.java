package nl.fontys.scope.net.handlers.responses;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.core.World;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.handlers.AbstractGameClientHandler;
import nl.fontys.scope.net.server.Responses;

public class GameReadyHandler extends AbstractGameClientHandler {

    public GameReadyHandler(GameClient client, World world) {
        super(client, world);
    }

    @Override
    public void handle(Connection connection, Object object) {
        // TODO
    }

    @Override
    public Class<?> getType() {
        return Responses.GameReady.class;
    }
}
