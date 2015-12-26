package nl.fontys.scope.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;

public class Weapon {

    private long timestamp;

    private long interval = 200;

    private static final int MUNITION_GAP = 5;

    private World world;

    private FocusContainer focus;

    private GameObjectFactory factory;

    private GameObject ship;

    private float munition;

    public Weapon(GameObject ship, World world, FocusContainer focus) {
        this.world = world;
        this.ship = ship;
        this.focus = focus;
        this.factory = new GameObjectFactory(world);
        this.timestamp = System.currentTimeMillis();
    }

    public void setInterval(long interval) {
        this.interval = Math.abs(interval);
    }

    public void shoot() {
        if (focus.hasFocus() && checkShooting()) {
            factory.createShot(ship);
            if (++munition >= MUNITION_GAP) {
                focus.reduce();
                munition = 0;
            }
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
