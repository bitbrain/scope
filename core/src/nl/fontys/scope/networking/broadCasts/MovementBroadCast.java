package nl.fontys.scope.networking.broadCasts;

import nl.fontys.scope.object.GameObject;

public class MovementBroadCast extends GameObjectBroadCast {

    private double x, y, z;

    public MovementBroadCast() {

    }

    public MovementBroadCast(double x, double y, double z, long startTime, long gameID, GameObject object) {
        super(startTime, gameID, object);
        this.x = x;
        this.y = y;
        this.z = z;
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
