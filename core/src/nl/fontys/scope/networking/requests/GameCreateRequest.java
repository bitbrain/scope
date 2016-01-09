package nl.fontys.scope.networking.requests;

public class GameCreateRequest {

    private int playerCount;

    private String gameName;

    public int getPlayerCount() {
        return playerCount;
    }

    public GameCreateRequest(int playerCount, String gameName) {
        this.playerCount = playerCount;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public GameCreateRequest(){}
}
