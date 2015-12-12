package nl.fontys.scope.core;

import com.badlogic.gdx.utils.Disposable;

import net.engio.mbassy.listener.Handler;

import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.object.GameObjectType;

public class GameLogicHandler implements Disposable {

    private Events events = Events.getInstance();

    private World world;

    private GameObjectFactory factory;

    public GameLogicHandler(World world, GameObjectFactory factory) {
        events.register(this);
        this.world = world;
        this.factory = factory;
    }

    @Handler
    public void handle(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.COLLISION)) {
            handleCollision((GameObject)event.getPrimaryParam(), (GameObject)event.getSecondaryParam(0));
        }
    }

    @Override
    public void dispose() {
        events.unregister(this);
    }

    private void handleCollision(GameObject objectA, GameObject objectB) {
        boolean isCurrentShip = objectA.equals(Player.getCurrent().getShip());
        if (isCurrentShip && GameObjectType.ENERGY.equals(objectB.getType())) {
            world.remove(objectB);
        }
    }
}
