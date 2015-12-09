package nl.fontys.scope.networking.responses;

import nl.fontys.scope.networking.broadCasts.MovementBroadCast;
import nl.fontys.scope.object.GameObject;

public class MovementUpdate {

    private long start;
    private double x,y,z;
    private GameObject object;

    public MovementUpdate(long start, double x, double y, double z, GameObject object) {
        this.start = start;
        this.x = x;
        this.y = y;
        this.z = z;
        this.object = object;
    }

    public MovementUpdate(MovementBroadCast movement){
        this(movement.getTimeStamp(), movement.getX(), movement.getY(), movement.getZ(), movement.getObject());
    }

    public GameObject getObject() {
        return object;
    }

    public long getStart() {
        return start;
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
}
