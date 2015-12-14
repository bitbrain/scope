package nl.fontys.scope.core;

import com.badlogic.gdx.Gdx;

import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;

public class Weapon {

    private long timestamp;

    private long interval = 1000;

    private World world;

    private GameObjectFactory factory;

    private GameObject ship;

    public Weapon(GameObject ship, World world) {
        this.world = world;
        this.ship = ship;
        this.factory = new GameObjectFactory(world);
        this.timestamp = System.currentTimeMillis();
    }

    public void setInterval(long interval) {
        this.interval = Math.abs(interval);
    }

    public void shoot() {
        if (checkShooting()) {
            // TODO shoot
        }
    }

    private boolean checkShooting() {
        if ((System.currentTimeMillis() - this.timestamp) >= interval) {
            this.timestamp = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }
}
