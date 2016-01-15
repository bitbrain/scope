package nl.fontys.scope.networking.responses;

import java.util.HashMap;

public class GetGamesResponse {
    private HashMap<String, Long> games;

    public HashMap<String, Long> getGames() {
        return games;
    }

    public void setGames(HashMap<String, Long> games) {
        this.games = games;
    }

    public GetGamesResponse(){}
}
