package nl.fontys.scope.networking.requests;


public class JoinRequest extends AbstractRequest{

    private long GameID;

    public long getGameID() {
        return GameID;
    }

    public JoinRequest(long clientID, long gameID) {
        super(clientID);
        GameID = gameID;
    }

    public JoinRequest(){}
}
