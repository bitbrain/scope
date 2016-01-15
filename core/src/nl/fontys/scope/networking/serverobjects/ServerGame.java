package nl.fontys.scope.networking.serverobjects;

import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;

public class ServerGame {

    private long ID;
    private String gameName;
    private HashMap<Long,Connection> players;
    private int PlayerCount;
    private boolean started;

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getPlayerCount() {
        return PlayerCount;
    }

    public long getID() {
        return ID;
    }

    public String getGameName() { return gameName; }

    public HashMap<Long,Connection> getPlayers() {
        return players;
    }

    public ServerGame(int playerCount, String gameName) {
        //this.ID = (long)(Math.random()*12131);//UUID.randomUUID().getMostSignificantBits();
        this.ID = 999;
        PlayerCount = playerCount;
        players = new HashMap<Long, Connection>();
        this.gameName = gameName;
    }
}
