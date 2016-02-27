package nl.fontys.scope.net.client;

import java.io.Serializable;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.object.GameObject;

public class Requests {

    // list of all requests

    public static class CreateGame extends Request {

        public CreateGame() {
            // noOp
        }

        public CreateGame(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class JoinGame extends Request {

        private String gameObjectId;

        public JoinGame() {
            // noOp
        }

        public JoinGame(String gameId, String clientId, String gameObjectId) {
            super(gameId, clientId);
            this.gameObjectId = gameObjectId;
        }

        public String getGameObjectId() {
            return gameObjectId;
        }
    }

    public static class WinGame extends Request {

        public WinGame() {
            // noOp
        }

        public WinGame(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class LeaveGame extends Request {

        public LeaveGame() {
            // noOp
        }

        public LeaveGame(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    public static class AddObject extends Request {

        private GameObject object;

        public AddObject() {
            // noOp
        }

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

        public RemoveObject() {
            // noOp
        }

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

        public UpdateObject() {
            // noOp
        }

        public UpdateObject(String gameId, String clientId, GameObject object) {
            super(gameId, clientId);
            this.object = object;
        }

        public GameObject getGameObject() {
            return object;
        }
    }

    public static class UpdatePlayer extends Request {

        public UpdatePlayer() {
            // noOp
        }

        public UpdatePlayer(String gameId, String clientId) {
            super(gameId, clientId);
        }
    }

    // private classes
    private static class Request implements Serializable {

        private String gameId;

        private String clientId;

        public Request(String gameId, String clientId) {
            this.gameId = gameId;
            this.clientId = clientId;
        }

        public Request() {
            // noOp
        }

        public String getGameId() {
            return gameId;
        }

        public String getClientId() {
            return clientId;
        }
    }
}
