package nl.fontys.scope.net.client;

public class Requests {

    // list of all requests

    public static class CreateGame extends Request {

        public CreateGame(String gameId) {
            super(gameId, "");
        }
    }

    public static class JoinGame extends Request {

        public JoinGame(String gameId) {
            super(gameId, "");
        }
    }

    public static class WinGame extends Request {

        public WinGame(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class LeaveGame extends Request {

        public LeaveGame(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class AddObject extends Request {

        public AddObject(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class RemoveObject extends Request {

        public RemoveObject(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class UpdateObject extends Request {

        public UpdateObject(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class UpdatePlayer extends Request {

        public UpdatePlayer(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    // private classes
    private static class Request {

        private String gameId;

        private String clientId;

        public Request(String gameId, String clientId) {
            this.gameId = gameId;
            this.clientId = clientId;
        }

        public String getGameId() {
            return gameId;
        }

        public String getClientId() {
            return clientId;
        }
    }
}
