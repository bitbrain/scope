package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

import nl.fontys.scope.net.server.ConnectionManager;
import nl.fontys.scope.net.server.GameInstanceManager;

public interface RequestHandler {

    void handle(Connection connection, Object object, GameInstanceManager gameInstanceManager);

    Class<?> getType();
}
