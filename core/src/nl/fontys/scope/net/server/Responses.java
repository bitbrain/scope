package nl.fontys.scope.net.server;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.object.GameObject;

public class Responses {

    // list of all responses
    public static class GameCreated extends Response {

        private String clientId;

        public GameCreated(String gameId, String clientId) {
            super(gameId);
            this.clientId = clientId;
        }

        public String getClientId() {
            return clientId;
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

    public static class PlayerJoined extends Response {

        private Player player;

        public PlayerJoined(String gameId, Player player) {
            super(gameId);
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }
    }

    public static class PlayerLeft extends Response {

        private String clientId;

        public PlayerLeft(String gameId, String clientId) {
            super(gameId);
            this.clientId = clientId;
        }
    }

    public static class GameObjectAdded extends Response {

        private GameObject object;

        private String clientId;

        public GameObjectAdded(String gameId, String clientId, GameObject object) {
            super(gameId);
            this.clientId = clientId;
            this.object = object;
        }

        public String getClientId() {
            return clientId;
        }

        public GameObject getGameObject() {
            return object;
        }
    }

    public static class GameObjectRemoved extends Response {

        private GameObject object;

        private String clientId;

        public GameObjectRemoved(String gameId, String clientId, GameObject object) {
            super(gameId);
            this.clientId = clientId;
            this.object = object;
        }

        public GameObject getGameObject() {
            return object;
        }

        public String getClientId() {
            return clientId;
        }
    }

    public static class GameObjectUpdated extends Response {

        private GameObject object;

        private String clientId;

        public GameObjectUpdated(String gameId, String clientId, GameObject object) {
            super(gameId);
            this.clientId = clientId;
            this.object = object;
        }

        public GameObject getGameObject() {
            return object;
        }

        public String getClientId() {
            return clientId;
        }
    }

    public static class PlayerUpdated extends Response {

        private Player player;

        private String clientId;

        public PlayerUpdated(String gameId, String clientId, Player player) {
            super(gameId);
            this.player = player;
            this.clientId = clientId;
        }

        public Player getPlayer() {
            return player;
        }

        public String getClientId() {
            return clientId;
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
