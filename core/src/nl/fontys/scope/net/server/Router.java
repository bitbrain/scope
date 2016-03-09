package nl.fontys.scope.net.server;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.net.handlers.Handler;

/**
 * Created by miguel on 23.02.16.
 */
public class Router {

    private Map<Class<?>, Handler> handlers;

    public Router() {
        handlers = new HashMap<Class<?>, Handler>();
    }

    public void registerHandler(Handler handler) {
        handlers.put(handler.getType(), handler);
    }

    public void route(Connection connection, Object object) {
        Class<?> key = object.getClass();
        if (handlers.containsKey(key)) {
            handlers.get(key).handle(connection, object);
        }
    }
}
