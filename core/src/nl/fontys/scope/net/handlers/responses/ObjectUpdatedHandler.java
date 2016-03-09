package nl.fontys.scope.net.handlers.responses;

import com.esotericsoftware.kryonet.Connection;

import java.util.logging.Logger;

import nl.fontys.scope.core.World;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.handlers.AbstractGameClientHandler;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.util.Mutator;

public class ObjectUpdatedHandler extends AbstractGameClientHandler {

    private static final Logger LOGGER = Logger.getLogger(ObjectUpdatedHandler.class.getName());

    public ObjectUpdatedHandler(GameClient client, World world) {
        super(client, world);
    }

    @Override
    public void handle(Connection connection, Object object) {
        final GameObject gameObject = ((Responses.GameObjectUpdated)object).getGameObject();
        GameObject localCopy = world.getObjectById(gameObject.getId());
        if (localCopy != null) {
            localCopy.set(gameObject);
        } else {
            LOGGER.warning("Could not update GameObject with id '" + gameObject.getId() + "'! It does not exist.");
        }
    }

    @Override
    public Class<?> getType() {
        return Responses.GameObjectUpdated.class;
    }
}
