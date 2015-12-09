package nl.fontys.scope.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.*;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;

import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.GameObjectController;
import nl.fontys.scope.networking.broadCasts.BroadcastRequest;
import nl.fontys.scope.networking.broadCasts.MovementBroadCast;
import nl.fontys.scope.networking.requests.JoinRequest;
import nl.fontys.scope.networking.responses.JoinedResponse;
import nl.fontys.scope.networking.responses.MovementUpdate;
import nl.fontys.scope.object.GameObject;

public class ScopeClient extends Listener implements GameObjectController{

    private boolean connected;
    private boolean joined;
    private long gameID;

    private GameObjectUpdater updater;

    private Client client;

    public static int TIMEOUT = 5000;

    public ScopeClient(World world) {

        client = new Client();

        updater = new GameObjectUpdater(world);

        world.addController(this);

        client.addListener(this);

        client.start();

    }

    public void received(Connection connection, Object object) {
        if (object instanceof JoinedResponse) {

            joined = ((JoinedResponse) object).isSuccessful();
            gameID = ((JoinedResponse) object).getGameID();
        }

        if (object instanceof MovementUpdate) {
            updater.updateGameObject(((MovementUpdate) object).getObject());
        }
    }



    public void joinGame(long GameID){
        client.sendTCP(new JoinRequest());
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
    public void update(GameObject object, float delta) {
        client.sendUDP(new MovementBroadCast(0,0,0, Gdx.graphics.getFrameId(), gameID , object));
    }
}
