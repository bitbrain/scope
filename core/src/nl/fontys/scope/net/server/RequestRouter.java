package nl.fontys.scope.net.server;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miguel on 23.02.16.
 */
public class RequestRouter {

    private Map<Class<?>, RequestHandler> handlers;

    public RequestRouter() {
        handlers = new HashMap<Class<?>, RequestHandler>();
    }

    public void registerHandler(Class<?> c, RequestHandler handler) {
        handlers.put(c, handler);
    }

    public void route(Connection connection, Object object, ConnectionManager manager) {
        Class<?> key = object.getClass();
        if (handlers.containsKey(key)) {
            handlers.get(key).handle(connection, object, manager);
        }
    }
}
