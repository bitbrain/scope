package nl.fontys.scope.net.server;

public class Responses {

    // list of all responses
    public static class GameCreated extends Response {

        public GameCreated(String gameId) {
            super(gameId);
        }
    }

    public static class GameReady extends Response {

        public GameReady(String gameId) {
            super(gameId);
        }
    }

    public static class GameOver extends Response {

        private String winnerId;

        public GameOver(String gameId, String winnerId) {
            super(gameId);
            this.winnerId = winnerId;
        }

        public String getWinnerId() {
            return winnerId;
        }
    }

    // private classes
    private static class Response {

        private String gameId;

        public Response(String gameId) {
            this.gameId = gameId;
        }

        public String getGameId() {
            return gameId;
        }
    }
}
