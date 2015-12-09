package nl.fontys.scope.networking.requests;

public class GameCreateRequest {

    private int PlayerCount;

    public int getPlayerCount() {
        return PlayerCount;
    }

    public GameCreateRequest(int playerCount) {
        PlayerCount = playerCount;
    }
}
