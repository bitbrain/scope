package nl.fontys.scope.networking.broadCasts;

import java.io.Serializable;

public abstract class  BroadcastRequest implements Serializable {

    long timeStamp;
    long gameID;

    public BroadcastRequest() {

    }

    public BroadcastRequest(long timeStamp, long gameID) {
        this.timeStamp = timeStamp;
        this.gameID = gameID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getGameID() {
        return gameID;
    }

}
