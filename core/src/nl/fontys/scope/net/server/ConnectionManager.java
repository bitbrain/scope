package nl.fontys.scope.net.server;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.handlers.CreateGameHandler;
import nl.fontys.scope.net.kryo.KryoConfig;

public class ConnectionManager implements Disposable {

    private class ConnectionListener extends Listener {

        private RequestRouter router;

        private GameInstanceManager gameInstanceManager;

        public ConnectionListener(GameInstanceManager gameInstanceManager) {
            router = new RequestRouter();
            this.gameInstanceManager = gameInstanceManager;
            setupHandlers();
        }

        @Override
        public void received(Connection connection, Object object) {
            router.route(connection, object, ConnectionManager.this, gameInstanceManager);
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
            router.registerHandler(Requests.CreateGame.class, new CreateGameHandler());
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
            server.bind(KryoConfig.TCP_PORT, KryoConfig.UDP_PORT);
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
