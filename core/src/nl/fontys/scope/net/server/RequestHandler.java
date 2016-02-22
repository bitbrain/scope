package nl.fontys.scope.net.server;

import com.esotericsoftware.kryonet.Connection;

public interface RequestHandler {

    void handle(Connection connection, Object object, ConnectionManager connectionManager);
}
