package nl.fontys.scope.net.handlers.responses;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.core.World;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.handlers.AbstractGameClientHandler;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.util.Mutator;

public class ObjectRemovedHandler extends AbstractGameClientHandler {

    public ObjectRemovedHandler(GameClient client, World world) {
        super(client, world);
    }

    @Override
    public void handle(Connection connection, Object object) {
        final String id = ((Responses.GameObjectRemoved)object).getGameObjectId();
        GameObject gameObject = world.getObjectById(id);
        if (gameObject != null) {
            world.remove(gameObject);
        }
    }

    @Override
    public Class<?> getType() {
        return Responses.GameObjectRemoved.class;
    }
}
