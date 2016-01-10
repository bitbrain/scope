package nl.fontys.scope.networking.requests;

public class GameCreateRequest extends AbstractRequest{

    private int playerCount;

    private String gameName;

    public int getPlayerCount() {
        return playerCount;
    }

    public GameCreateRequest(long clientID, int playerCount, String gameName) {
        super(clientID);
        this.playerCount = playerCount;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public GameCreateRequest(){}
}
