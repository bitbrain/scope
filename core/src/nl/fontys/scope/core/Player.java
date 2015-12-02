package nl.fontys.scope.core;

import nl.fontys.scope.object.GameObject;

public class Player {

    private static Player current = new Player();

    private GameObject ship;

    private Player() {

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
