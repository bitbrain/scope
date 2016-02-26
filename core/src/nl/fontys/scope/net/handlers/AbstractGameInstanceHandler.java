package nl.fontys.scope.net.handlers;

import nl.fontys.scope.net.server.GameInstanceManager;

public abstract class AbstractGameInstanceHandler implements nl.fontys.scope.net.handlers.Handler {

    protected GameInstanceManager gameInstanceManager;

    public AbstractGameInstanceHandler(GameInstanceManager gameInstanceManager) {
        this.gameInstanceManager = gameInstanceManager;
    }
}
