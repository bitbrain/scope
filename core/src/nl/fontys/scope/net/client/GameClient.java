package nl.fontys.scope.net.client;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import net.engio.mbassy.listener.Handler;

import java.io.IOException;

import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.World;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.net.handlers.responses.ClientJoinedHandler;
import nl.fontys.scope.net.handlers.responses.ObjectAddedHandler;
import nl.fontys.scope.net.handlers.responses.ObjectRemovedHandler;
import nl.fontys.scope.net.handlers.responses.ObjectUpdatedHandler;
import nl.fontys.scope.net.kryo.KryoConfig;
import nl.fontys.scope.net.server.Router;
import nl.fontys.scope.object.GameObject;

public class GameClient extends Listener implements Disposable {

    private Events events;

    private Client client;

    private String gameId;

    private String clientId;

    private Router router;

    public GameClient(Events events, String gameId, World world, PlayerManager playerManager) {
        this.events = events;
        this.gameId = gameId;
        this.clientId = PlayerManager.getCurrent().getId();
        this.events.register(this);
        this.client = new Client();
        this.router = new Router();
        this.client.addListener(this);
        this.client.start();
        KryoConfig.configure(client.getKryo());
        setupHandlers(world, playerManager);
    }

    public void connect(boolean createNewGame) {
        this.client.start();
        try {
            this.client.connect(5000, "localhost", KryoConfig.TCP_PORT, KryoConfig.UDP_PORT);
            if (createNewGame) {
                this.client.sendTCP(new Requests.CreateGame(gameId, clientId));
            } else {
                this.client.sendTCP(new Requests.JoinGame(gameId, clientId, PlayerManager.getCurrent().getShip().getId()));
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
        events.unregister(this);
        this.client.close();
    }

    @Override
    public void received(Connection connection, Object object) {
        router.route(connection, object);
    }

    @Handler
    public void onEvent(Events.GdxEvent event) {
        if (client.isConnected()) {
            if (event.isTypeOf(EventType.OBJECT_CREATED)) {
                GameObject object = (GameObject) event.getPrimaryParam();
                if (object.getClientId().equals(clientId)) {
                    client.sendTCP(new Requests.AddObject(gameId, clientId, object));
                }
            } else if (event.isTypeOf(EventType.OBJECT_REMOVED)) {
                GameObject object = (GameObject) event.getPrimaryParam();
                if (object.getClientId().equals(clientId)) {
                    client.sendTCP(new Requests.RemoveObject(gameId, clientId, object.getId()));
                }
            } else if (event.isTypeOf(EventType.GAME_OVER)) {
                if (PlayerManager.getCurrent().getGameProgress() == 1f) {
                    client.sendTCP(new Requests.WinGame(gameId, clientId));
                }
            }
        }
    }

    private void setupHandlers(World world, PlayerManager playerManager) {
        router.registerHandler(new ObjectAddedHandler(this, world));
        router.registerHandler(new ObjectRemovedHandler(this, world));
        router.registerHandler(new ObjectUpdatedHandler(this, world));
        router.registerHandler(new ClientJoinedHandler(this, world, playerManager));
    }
}
