package nl.fontys.scope.networking.broadCasts;

import nl.fontys.scope.object.GameObject;

public class MovementBroadCast extends BroadcastRequest {

    private double x, y, z;

    private long GameID;


    public MovementBroadCast(double x, double y, double z, long startTime, long gameID, GameObject object) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timeStamp = startTime;
        GameID = gameID;
        this.object = object;
    }

    public GameObject getObject() {
        return object;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getGameID() {
        return GameID;
    }
}
