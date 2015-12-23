package nl.fontys.scope.core;

import java.util.UUID;

import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;

public class Player {

    public static final int POINT_GAP = 500;

    private FocusContainer focus;

    private GameObject ship;

    private String id;

    private int points;

    private Events events = Events.getInstance();

    private Weapon weapon;

    Player(World world) {
        this.id = UUID.randomUUID().toString();
        focus = new FocusContainer();
        GameObjectFactory factory = new GameObjectFactory(world);
        this.ship = factory.createShip(0f, 0f, 0f);
        this.weapon = new Weapon(this.ship, world, focus);
    }

    public String getId() {
        return id;
    }

    public float getFocusProgress() {
        return focus.getProgress();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getFocusCount() {
        return focus.getFocus();
    }

    public void addFocus() {
        focus.addFocus();
    }

    public int clearFocus() {
        int count = focus.getFocus();
        focus.clear();
        events.fire(EventType.ENERGY_DROPPED, this, count);
        return count;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        if (points > 0) {
            this.points += points;
            events.fire(EventType.POINTS_GAINED, this, points);
        }
    }

    public float getGameProgress() {
        return (float)this.points / (float)POINT_GAP;
    }

    public GameObject getShip() {
        return ship;
    }


    public boolean isCurrentPlayer() {
        return this.equals(PlayerManager.getCurrent());
    }
}
