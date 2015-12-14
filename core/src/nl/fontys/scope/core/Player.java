package nl.fontys.scope.core;

import java.util.UUID;

import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;

public class Player {

    private GameObject ship;

    private String id;

    private int energyCount;

    private int points;

    private Events events = Events.getInstance();

    Player(GameObjectFactory factory) {
        this.id = UUID.randomUUID().toString();
        this.ship = factory.createShip(0f, 0f, 0f);
    }

    public String getId() {
        return id;
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

    public GameObject getShip() {
        return ship;
    }


}
