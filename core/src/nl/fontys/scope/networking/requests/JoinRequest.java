package nl.fontys.scope.networking.requests;

public class JoinRequest {

    private long GameID;

    public long getGameID() {
        return GameID;
    }

    public JoinRequest(long gameID) {
        GameID = gameID;
    }
}
