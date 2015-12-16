package nl.fontys.scope.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.*;
import com.esotericsoftware.kryonet.Client;

import net.engio.mbassy.listener.Handler;

import java.io.IOException;
import java.net.InetAddress;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.GameObjectController;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.networking.broadCasts.CollisionBroadCast;
import nl.fontys.scope.networking.broadCasts.GameObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.MovementBroadCast;
import nl.fontys.scope.networking.broadCasts.ObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.PlayerBroadCast;
import nl.fontys.scope.networking.requests.JoinRequest;
import nl.fontys.scope.networking.responses.GameStartedResponse;
import nl.fontys.scope.networking.responses.JoinedResponse;
import nl.fontys.scope.object.GameObject;

public class ScopeClient extends Listener implements GameObjectController{

    private boolean connected;
    private boolean joined;
    private long gameID;

    private GameObjectUpdater updater;

    private Client client;

    private Events events = Events.getInstance();

    public static int TIMEOUT = 5000;

    public ScopeClient(World world) {

        client = new Client();

        updater = new GameObjectUpdater(world);

        world.addController(this);

        events.register(this);

        ScopeNetworkingHelper.registerClasses(client.getKryo());

        client.addListener(this);

        client.start();

    }

    public void received(Connection connection, Object object) {
        if (object instanceof JoinedResponse) {

            joined = ((JoinedResponse) object).isSuccessful();
            gameID = ((JoinedResponse) object).getGameID();
        }

        if (object instanceof GameObjectBroadCast) {
            updater.updateGameObject(((GameObjectBroadCast) object).getObject());
        }

        if (object instanceof ObjectBroadCast) {
            if (((ObjectBroadCast) object).getEventType() == EventType.OBJECT_CREATED){
               updater.createGameObject(((ObjectBroadCast) object).getObject());
            }
            else if ((((ObjectBroadCast) object).getEventType() == EventType.OBJECT_REMOVED)){
                updater.removeGameObject(((ObjectBroadCast) object).getObject());
            }
        }

        if (object instanceof GameStartedResponse) {
            events.fire(EventType.GAME_START, null, null);
        }
    }

    @Handler
    private void handleEvents(Events.GdxEvent event){
        if (event.isTypeOf(EventType.OBJECT_CREATED) || event.isTypeOf(EventType.OBJECT_REMOVED)){
            client.sendTCP(new ObjectBroadCast(Gdx.graphics.getFrameId(), gameID,
                    (GameObject) event.getPrimaryParam(), event.getType()));
        }
        else if (event.isTypeOf(EventType.COLLISION) || event.isTypeOf(EventType.COLLISION_FULL)){
            client.sendTCP(new CollisionBroadCast(Gdx.graphics.getFrameId(), gameID,
                    (GameObject) event.getPrimaryParam(),
                    (GameObject) event.getSecondaryParam(0),
                    event.isTypeOf(EventType.COLLISION_FULL)));
        }
        else if (event.isTypeOf(EventType.ENERGY_DROPPED) || event.isTypeOf(EventType.POINTS_GAINED)){
            client.sendTCP(new PlayerBroadCast(Gdx.graphics.getFrameId(), gameID,
                    (Player) event.getPrimaryParam(),
                    (Integer) event.getSecondaryParam(0),
                    event.getType()));
        }
    }

    public void joinGame(long gameID){
        client.sendTCP(new JoinRequest(gameID));
    }

    public void connectToServer(InetAddress serverAddress, int tcpPort, int udpPort){
        try {
            client.connect(TIMEOUT, serverAddress, tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        connected = true;
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        //noOp
    }

    @Override
    public void update(GameObject object, float delta) {
        if (object.hasMoved()) {
            client.sendUDP(new GameObjectBroadCast(Gdx.graphics.getFrameId(), gameID, object));
        }
    }
}
