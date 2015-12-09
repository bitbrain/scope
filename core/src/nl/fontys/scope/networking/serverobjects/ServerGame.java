package nl.fontys.scope.networking.serverobjects;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by jankerkenhoff on 09/12/15.
 */
public class ServerGame {

    private long ID;
    private HashSet<Connection> players;
    private int PlayerCount;

    public int getPlayerCount() {
        return PlayerCount;
    }

    public long getID() {
        return ID;
    }


    public HashSet<Connection> getPlayers() {
        return players;
    }

    public ServerGame(int playerCount) {
        this.ID = UUID.randomUUID().timestamp();
        PlayerCount = playerCount;
        players = new HashSet<Connection>();
    }
}
