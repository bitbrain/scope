package nl.fontys.scope.networking.broadCasts;

import nl.fontys.scope.object.GameObject;

public abstract class  BroadcastRequest {

    long timeStamp;
    long gameID;


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
