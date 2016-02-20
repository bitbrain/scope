package nl.fontys.scope.net.kryo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.net.GameInstance;
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
    }
}
