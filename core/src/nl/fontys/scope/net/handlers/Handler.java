package nl.fontys.scope.net.handlers;

import com.esotericsoftware.kryonet.Connection;

public interface Handler {

    void handle(Connection connection, Object object);

    Class<?> getType();
}
