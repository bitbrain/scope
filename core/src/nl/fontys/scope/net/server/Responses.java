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

        public GameOver(String gameId) {
            super(gameId);
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
