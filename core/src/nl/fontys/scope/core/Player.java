package nl.fontys.scope.core;

import java.util.UUID;

import nl.fontys.scope.Config;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;

public class Player {

    private FocusContainer focus;

    private GameObject ship;

    private String id;

    private int points;

    private Events events = Events.getInstance();

    private Weapon weapon;

    private int number;

    private float health = 1f;

    private boolean ai;

    Player(World world) {
        this.id = UUID.randomUUID().toString();
        focus = new FocusContainer();
        GameObjectFactory factory = new GameObjectFactory(world);
        this.ship = factory.createShip(0f, 0f, 0f);
        this.weapon = new Weapon(this.ship, world, focus);
    }

    public boolean isAI() {
        return ai;
    }

    public void setAI(boolean ai) {
        this.ai = ai;
    }

    public float getHealth() {
        return health;
    }

    public void heal() {
        this.health = 1f;
    }

    public void addDamage(float percentage) {
        health -= Math.abs(percentage);
        if (health <= 0f) {
            health = 0f;
            events.fire(EventType.PLAYER_DEAD, this);
        }
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
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
        if (points > 0 && getGameProgress() < 1f) {
            this.points += points;
            events.fire(EventType.POINTS_GAINED, this, points);
            if (getGameProgress() == 1f) {
                events.fire(EventType.GAME_OVER, this);
            }
        }
    }

    public float getGameProgress() {
        return Math.min((float)this.points / (float) Config.POINT_GAP, 1f);
    }

    public GameObject getShip() {
        return ship;
    }


    public boolean isCurrentPlayer() {
        return this.equals(PlayerManager.getCurrent());
    }
}
