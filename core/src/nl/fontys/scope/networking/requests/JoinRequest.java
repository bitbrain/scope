package nl.fontys.scope.networking.requests;

import nl.fontys.scope.networking.responses.JoinedResponse;

public class JoinRequest {

    private long GameID;

    public long getGameID() {
        return GameID;
    }

    public JoinRequest(long gameID) {

        GameID = gameID;
    }

    public JoinRequest(){}
}
