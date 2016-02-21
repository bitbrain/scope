package nl.fontys.scope.net.client;

public class Requests {

    // list of all requests

    public static class CreateGame extends Request {

        public CreateGame(String gameId) {
            super(gameId);
        }
    }

    public static class JoinGame extends Request {

        public JoinGame(String gameId) {
            super(gameId);
        }
    }

    public static class WinGame extends Request {

        private String clientId;

        public WinGame(String gameId, String clientId) {
            super(gameId);
            this.clientId = clientId;
        }
    }

    // private classes
    private static class Request {

        private String gameId;

        public Request(String gameId) {
            this.gameId = gameId;
        }

        public String getGameId() {
            return gameId;
        }
    }
}
