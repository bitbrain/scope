package nl.fontys.scope.net.client;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.object.GameObject;

public class Requests {

    // list of all requests

    public static class CreateGame extends Request {

        public CreateGame(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class JoinGame extends Request {

        public JoinGame(String gameId, String clientId) {
            super(gameId, clientId);
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

        private GameObject object;

        public AddObject(String gameId, String clientId, GameObject object) {
            super(gameId, clientId);
            this.object = object;
        }

        public GameObject getGameObject() {
            return object;
        }
    }

    public static class RemoveObject extends Request {

        private String gameObjectId;

        public RemoveObject(String gameId, String clientId, String gameObjectId) {
            super(gameId, clientId);
            this.gameObjectId = gameObjectId;
        }

        public String getGameObjectId() {
            return gameObjectId;
        }
    }

    public static class UpdateObject extends Request {

        private GameObject object;

        public UpdateObject(String gameId, String clientId, GameObject object) {
            super(gameId, clientId);
            this.object = object;
        }

        public GameObject getGameObject() {
            return object;
        }
    }

    public static class UpdatePlayer extends Request {

        private Player player;

        public UpdatePlayer(String gameId, String clientId, Player player) {
            super(gameId, clientId);
            this.player = player;
        }

        public Player getPlayer() {
            return player;
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
