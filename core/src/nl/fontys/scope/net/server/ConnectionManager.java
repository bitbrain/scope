package nl.fontys.scope.net.server;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import nl.fontys.scope.net.kryo.KryoConfig;

public class ConnectionManager implements Disposable {

    private class ConnectionListener extends Listener {

        @Override
        public void received(Connection connection, Object object) {
            System.out.println("received " + object);
            super.received(connection, object);
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
    }

    private Server server;

    public ConnectionManager() {
        this.server = new Server();
        this.server.addListener(new ConnectionListener());
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
