package nl.fontys.scope.networking.broadCasts;

import com.badlogic.gdx.Game;

import nl.fontys.scope.event.EventType;
import nl.fontys.scope.object.GameObject;


public class ObjectBroadCast extends GameObjectBroadCast {

    private String eventType;

    public ObjectBroadCast() {

    }

    public ObjectBroadCast(long clientID,long startTime, long gameID, GameObject object, String eventType) {
        super(clientID, startTime, gameID, object);
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

}
