package nl.fontys.scope.net.kryo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.net.client.Requests;
import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

public class KryoConfig {

    public static final int TCP_PORT = 54555;

    public static final int UDP_PORT = 54777;

    public static void configure(Kryo kryo) {
        // Core classes
        kryo.register(java.util.HashMap.class);
        kryo.register(java.util.HashSet.class);
        kryo.register(GameInstance.class);
        kryo.register(com.esotericsoftware.kryonet.Connection.class);
        kryo.register(com.esotericsoftware.kryonet.Server.class);
        kryo.register(com.esotericsoftware.kryonet.Connection[].class);
        kryo.register(GameObject.class);
        kryo.register(Color.class);
        kryo.register(Player.class);
        kryo.register(GameObjectType.class);
        kryo.register(Vector3.class);
        kryo.register(Quaternion.class);

        // Requests
        kryo.register(Requests.JoinGame.class);
        kryo.register(Requests.CreateGame.class);
        kryo.register(Requests.WinGame.class);
        kryo.register(Requests.AddObject.class);
        kryo.register(Requests.RemoveObject.class);
        kryo.register(Requests.UpdateObject.class);
        kryo.register(Requests.LeaveGame.class);
        kryo.register(Requests.UpdatePlayer.class);

        // Responses
        kryo.register(Responses.GameCreated.class);
        kryo.register(Responses.GameOver.class);
        kryo.register(Responses.GameReady.class);
        kryo.register(Responses.ClientJoined.class);
        kryo.register(Responses.ClientLeft.class);
        kryo.register(Responses.ClientUpdated.class);
        kryo.register(Responses.GameObjectAdded.class);
        kryo.register(Responses.GameObjectRemoved.class);
        kryo.register(Responses.GameObjectUpdated.class);
    }
}
