package nl.fontys.scope.net.handlers.responses;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.core.World;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.handlers.AbstractGameClientHandler;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.util.Mutator;

public class ObjectAddedHandler extends AbstractGameClientHandler {

    public ObjectAddedHandler(GameClient client, World world) {
        super(client, world);
    }

    @Override
    public void handle(Connection connection, Object object) {
        final GameObject gameObject = ((Responses.GameObjectAdded)object).getGameObject();
        world.createGameObject(new Mutator<GameObject>() {
            @Override
            public void mutate(GameObject target) {
                target.set(gameObject);
            }
        });
    }

    @Override
    public Class<?> getType() {
        return Responses.GameObjectAdded.class;
    }
}
