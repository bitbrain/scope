package nl.fontys.scope.core;

import java.util.HashMap;

import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;

public class PlayerManager {

    private final GameObjectFactory factory;

    private static Player current;

    private HashMap<String, Player> players = new HashMap<String, Player>();

    private HashMap<GameObject, Player> shipToPlayers = new HashMap<GameObject, Player>();
    
    public PlayerManager(GameObjectFactory factory) {
        this.factory = factory;
        this.current = addPlayer();
    }

    public static Player getCurrent() {
        return current;
    }

    public Player getPlayerByShip(GameObject ship) {
        return shipToPlayers.get(ship);
    }

    public Player addPlayer() {
        Player player = new Player(factory);
        players.put(player.getId(), player);
        return player;
    }
}
