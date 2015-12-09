package nl.fontys.scope.networking.responses;

import java.util.HashMap;

import nl.fontys.scope.networking.serverobjects.ServerGame;

public class GetGamesResponse {
    private HashMap<Long, ServerGame> games;

    public HashMap<Long, ServerGame> getGames() {
        return games;
    }

    public void setGames(HashMap<Long, ServerGame> games) {
        this.games = games;
    }
}
