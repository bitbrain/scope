package nl.fontys.scope.networking.requests;

public class GameStartedCheckRequest {

    public GameStartedCheckRequest() {}

    public GameStartedCheckRequest(long gameID) {
        super();
        this.gameID = gameID;
    }

    public long gameID;
}
