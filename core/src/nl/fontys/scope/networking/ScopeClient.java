package nl.fontys.scope.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.*;
import com.esotericsoftware.kryonet.Client;

import net.engio.mbassy.listener.Handler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.GameObjectController;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.networking.broadCasts.CollisionBroadCast;
import nl.fontys.scope.networking.broadCasts.GameObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.ObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.PlayerBroadCast;
import nl.fontys.scope.networking.requests.GameCreateRequest;
import nl.fontys.scope.networking.requests.GameStartedCheckRequest;
import nl.fontys.scope.networking.requests.GetGamesRequest;
import nl.fontys.scope.networking.requests.JoinRequest;
import nl.fontys.scope.networking.responses.GameStartedCheckResponse;
import nl.fontys.scope.networking.responses.GameStartedResponse;
import nl.fontys.scope.networking.responses.GetGamesResponse;
import nl.fontys.scope.networking.responses.JoinedResponse;
import nl.fontys.scope.networking.serverobjects.ServerGame;
import nl.fontys.scope.object.GameObject;

public class ScopeClient extends Listener implements GameObjectController{

    private boolean connected;
    private boolean joined;
    private long gameID;
    private boolean started;
    private long clientID;

    public boolean isStarted() {
        if (!startedRefreshing){
            gameStarted();
            startedRefreshing = true;
        }
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isJoined() {
        return joined;
    }

    public long getGameID() {
        return gameID;
    }

    private GameObjectUpdater updater;

    private Client client;

    private Events events = Events.getInstance();

    public static int TIMEOUT = 5000;

    private boolean gamesListRefreshed = false;
    private boolean startedRefreshing = false;

    private HashMap<String, Long> gamesList;

    public ScopeClient(World world) {

        events.register(this);

        client = new Client();

        updater = new GameObjectUpdater(world);

        world.addController(this);

        ScopeNetworkingHelper.registerClasses(client.getKryo());

        client.addListener(this);

        client.start();

        clientID = (long)(Math.random()*176532342);

    }

    public void received(Connection connection, Object object) {
        if (object instanceof JoinedResponse) {
            joined = ((JoinedResponse) object).isSuccessful();
            gameID = ((JoinedResponse) object).getGameID();
            System.out.println("Joined Game " + gameID);
        }

        if (object instanceof GameStartedCheckResponse) {
            started = ((GameStartedCheckResponse) object).started;
            startedRefreshing = false;
        }

        if (object instanceof GetGamesResponse) {
             gamesList = ((GetGamesResponse) object).getGames();
             gamesListRefreshed = true;
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
            System.out.println("Start Event received from server");
            events.fire(EventType.GAME_START, null, null);
        }
    }

    @Handler
    private void handleEvents(Events.GdxEvent event){
        System.out.println("Client send Event" + event.getType().toString() + " gameID: " + gameID);

        if (event.isTypeOf(EventType.OBJECT_CREATED) || event.isTypeOf(EventType.OBJECT_REMOVED)){
            client.sendTCP(new ObjectBroadCast(clientID, Gdx.graphics.getFrameId(), gameID,
                    (GameObject) event.getPrimaryParam(), event.getType()));
        }
        else if (event.isTypeOf(EventType.COLLISION) || event.isTypeOf(EventType.COLLISION_FULL)){
            client.sendTCP(new CollisionBroadCast(clientID, Gdx.graphics.getFrameId(), gameID,
                    (GameObject) event.getPrimaryParam(),
                    (GameObject) event.getSecondaryParam(0),
                    event.isTypeOf(EventType.COLLISION_FULL)));
        }
        else if (event.isTypeOf(EventType.ENERGY_DROPPED) || event.isTypeOf(EventType.POINTS_GAINED)){
            client.sendTCP(new PlayerBroadCast(clientID, Gdx.graphics.getFrameId(), gameID,
                    (Player) event.getPrimaryParam(),
                    (Integer) event.getSecondaryParam(0),
                    event.getType()));
        }
    }

    public void joinGame(long gameID){
        client.sendTCP(new JoinRequest(clientID, gameID));
    }

    public long searchGame(String gameName){
        gamesListRefreshed = false;
        client.sendTCP(new GetGamesRequest(gameName));
        while (!gamesListRefreshed) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return gamesList.get(gameName);

    }

    public InetAddress findServer(){
        return client.discoverHost(ScopeNetworkingHelper.UDP_PORT,  5000);
    }

    public void connectToServer(InetAddress serverAddress, int tcpPort, int udpPort){
        try {
            client.connect(TIMEOUT, serverAddress, tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        connected = true;
    }

    public void createGame(int playerCount, String gameName){
        client.sendTCP(new GameCreateRequest(clientID, playerCount, gameName));
    }

    private void gameStarted(){
        client.sendTCP(new GameStartedCheckRequest(gameID));
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        //noOp
    }

    @Override
    public void update(GameObject object, float delta) {
        if (object.hasMoved()) {
            client.sendUDP(new GameObjectBroadCast(clientID, Gdx.graphics.getFrameId(), gameID, object));
        }

    }
}
