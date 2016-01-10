package nl.fontys.scope.networking.broadCasts;

import com.badlogic.gdx.Game;

import nl.fontys.scope.object.GameObject;

public class CollisionBroadCast extends GameObjectBroadCast {

    private GameObject other;
    private boolean fullCollision;

    public CollisionBroadCast() {

    }

    public GameObject getOther() {
        return other;
    }

    public CollisionBroadCast(long startTime, long gameID, GameObject object, GameObject other, boolean fullCollision) {
        super(startTime, gameID, object);
        this.other = other;
        this.fullCollision= fullCollision;
    }

    public boolean isFullCollision() {
        return fullCollision;
    }
}
