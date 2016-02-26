package nl.fontys.scope.net.handlers;

import nl.fontys.scope.net.server.GameInstanceManager;

public abstract class AbstractGameInstanceHandler implements Handler {

    protected GameInstanceManager gameInstanceManager;

    AbstractGameInstanceHandler(GameInstanceManager gameInstanceManager) {
        this.gameInstanceManager = gameInstanceManager;
    }
}
