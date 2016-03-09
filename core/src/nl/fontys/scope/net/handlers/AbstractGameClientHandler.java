package nl.fontys.scope.net.handlers;

import nl.fontys.scope.core.World;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.handlers.Handler;

public abstract class AbstractGameClientHandler implements Handler {

    protected World world;

    protected GameClient client;

    public AbstractGameClientHandler(GameClient client, World world) {
        this.client = client;
        this.world = world;
    }
}
