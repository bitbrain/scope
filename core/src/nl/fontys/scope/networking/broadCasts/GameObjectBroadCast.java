package nl.fontys.scope.networking.broadCasts;

import nl.fontys.scope.object.GameObject;

public class GameObjectBroadCast extends BroadcastRequest {

    private GameObject object;

    public GameObjectBroadCast() {
    }

    public GameObjectBroadCast(long clientID, long timeStamp, long gameID, GameObject object ) {
        super(clientID, timeStamp, gameID);
        this.object = object;
    }

    public GameObject getObject() {
        return object;
    }
}
