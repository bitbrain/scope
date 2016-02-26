package nl.fontys.scope.net.server;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import nl.fontys.scope.Config;
import nl.fontys.scope.net.handlers.requests.AddObjectHandler;
import nl.fontys.scope.net.handlers.requests.CreateGameHandler;
import nl.fontys.scope.net.handlers.requests.JoinGameHandler;
import nl.fontys.scope.net.handlers.requests.LeaveGameHandler;
import nl.fontys.scope.net.handlers.requests.RemoveObjectHandler;
import nl.fontys.scope.net.handlers.requests.UpdateObjectHandler;
import nl.fontys.scope.net.handlers.requests.WinGameHandler;
import nl.fontys.scope.net.kryo.KryoConfig;

public class ConnectionManager implements Disposable {

    private class ConnectionListener extends Listener {

        private Router router;

        private GameInstanceManager gameInstanceManager;

        public ConnectionListener(GameInstanceManager gameInstanceManager) {
            router = new Router();
            this.gameInstanceManager = gameInstanceManager;
            setupHandlers();
        }

        @Override
        public void received(Connection connection, Object object) {
            router.route(connection, object);
        }

        @Override
        public void idle(Connection connection) {
            System.out.println("Idle..");
            super.idle(connection);
        }

        @Override
        public void connected(Connection connection) {
            System.out.println("Connected!");
            super.connected(connection);
        }

        @Override
        public void disconnected(Connection connection) {
            System.out.println("Disconnected!");
            super.disconnected(connection);
        }

        private void setupHandlers() {
            router.registerHandler(new CreateGameHandler(gameInstanceManager));
            router.registerHandler(new JoinGameHandler(gameInstanceManager));
            router.registerHandler(new WinGameHandler(gameInstanceManager));
            router.registerHandler(new LeaveGameHandler(gameInstanceManager));
            router.registerHandler(new AddObjectHandler(gameInstanceManager));
            router.registerHandler(new RemoveObjectHandler(gameInstanceManager));
            router.registerHandler(new UpdateObjectHandler(gameInstanceManager));
        }
    }

    private Server server;

    public ConnectionManager(GameInstanceManager gameInstanceManager) {
        this.server = new Server();
        this.server.addListener(new ConnectionListener(gameInstanceManager));
        KryoConfig.configure(server.getKryo());
    }

    public void start() throws nl.fontys.scope.net.server.GameServerException {
        server.start();
        try {
        server.bind(Config.SERVER_TCP_PORT, Config.SERVER_UDP_PORT);
        } catch (IOException e) {
            throw new GameServerException("Could not start connection manager: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {
        server.stop();
        try {
            server.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
