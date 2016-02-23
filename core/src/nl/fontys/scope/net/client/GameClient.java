package nl.fontys.scope.net.client;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import net.engio.mbassy.listener.Handler;

import java.io.IOException;

import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.net.kryo.KryoConfig;

public class GameClient extends Listener implements Disposable {

    private Events events;

    private Client client;

    private String gameId;

    private String clientId;

    public GameClient(Events events, String gameId) {
        this.events = events;
        this.gameId = gameId;
        this.clientId = PlayerManager.getCurrent().getId();
        this.events.register(this);
        this.client = new Client();
        this.client.addListener(this);
        this.client.start();
        KryoConfig.configure(client.getKryo());
    }

    public void connect(boolean createNewGame) {
        this.client.start();
        try {
            this.client.connect(5000, "localhost", KryoConfig.TCP_PORT, KryoConfig.UDP_PORT);
            if (createNewGame) {
                this.client.sendTCP(new Requests.CreateGame(gameId, clientId));
            } else {
                this.client.sendTCP(new Requests.JoinGame(gameId, clientId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leaveCurrentGame() {
        if (this.client.isConnected()) {
            this.client.sendTCP(new Requests.LeaveGame(gameId, clientId));
            dispose();
        }
    }

    @Override
    public void dispose() {
        this.client.close();
    }

    @Override
    public void received(Connection connection, Object object) {
        // TODO
    }

    @Handler
    public void onEvent(Events.GdxEvent event) {
        // TODO
    }
}
