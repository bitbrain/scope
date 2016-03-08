package nl.fontys.scope.core;

import com.badlogic.gdx.graphics.Color;

import java.util.UUID;

import nl.fontys.scope.Config;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;

public class Player {

    private FocusContainer focus;

    private String shipId;

    private String id;

    private int points;

    private Events events = Events.getInstance();

    private Weapon weapon;

    private int number;

    private float health = 1f;

    private boolean ai;

    private Color color;

    private World world;

    Player(World world, String clientId, String shipId) {
        this.id = clientId != null ? clientId : UUID.randomUUID().toString();
        focus = new FocusContainer();
        this.world = world;
        GameObjectFactory factory = new GameObjectFactory(world);
        this.shipId = shipId != null ? shipId : factory.createShip(0f, 0f, 0f, this).getId();
        this.weapon = new Weapon(this.shipId, world, focus);
        color = Color.WHITE.cpy();
    }

    public boolean isAI() {
        return ai;
    }

    public Color getColor() {
        return color;
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
        return world.getObjectById(shipId);
    }


    public boolean isCurrentPlayer() {
        return this.equals(PlayerManager.getCurrent());
    }
}
