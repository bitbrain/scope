package nl.fontys.scope.networking.broadCasts;

public abstract class  BroadcastRequest{

    long timeStamp;
    long gameID;
    long clientID;

    public BroadcastRequest() {
    }

    public BroadcastRequest(long timeStamp, long gameID, long clientID) {
        this.timeStamp = timeStamp;
        this.gameID = gameID;
        this.clientID = clientID;
    }

    public long getClientID() {
        return clientID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getGameID() {
        return gameID;
    }

}
