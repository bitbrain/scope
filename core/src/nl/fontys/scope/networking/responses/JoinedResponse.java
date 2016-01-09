package nl.fontys.scope.networking.responses;

public class JoinedResponse {

    public boolean successful;
    public long gameID;

    public long getGameID() {
        return gameID;
    }

    public boolean isSuccessful() {

        return successful;
    }

    public JoinedResponse(long gameID) {
        this.gameID = gameID;
        this.successful = true;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public JoinedResponse(){}
}
