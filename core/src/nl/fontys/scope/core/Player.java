package nl.fontys.scope.core;

import nl.fontys.scope.object.GameObject;

public class Player {

    private static Player current = new Player();

    private GameObject ship;

    private int energyCount;

    private int points;

    private Player() {

    }

    public int getEnergyCount() {
        return energyCount;
    }

    public void addEnergy() {
        energyCount++;
    }

    public int dropEnergy() {
        int count = energyCount;
        energyCount = 0;
        return count;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points = points;
    }

    public static void setCurrent(GameObject ship) {
        getCurrent().ship = ship;
    }

    public static Player getCurrent() {
        return current;
    }

    public GameObject getShip() {
        return ship;
    }


}
