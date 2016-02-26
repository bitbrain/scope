package nl.fontys.scope.core;

import java.util.Collection;
import java.util.HashMap;

import nl.fontys.scope.object.GameObject;

public class PlayerManager {

    private static Player current;

    private HashMap<String, Player> players = new HashMap<String, Player>();

    private HashMap<GameObject, Player> shipToPlayers = new HashMap<GameObject, Player>();

    private World world;
    
    public PlayerManager(World world) {
        this.world = world;
        this.current = addPlayer();
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }

    public static Player getCurrent() {
        return current;
    }

    public static boolean isLocalObject(GameObject object) {
        return current.getId().equals(object.getClientId());
    }

    public Player getPlayerByShip(GameObject ship) {
        return shipToPlayers.get(ship);
    }

    public Player addPlayer() {
        return addPlayer(null, null);
    }

    public Player addPlayer(String clientId, String shipId) {
        Player player = new Player(world, clientId, shipId);
        players.put(player.getId(), player);
        player.setNumber(players.size());
        shipToPlayers.put(player.getShip(), player);
        return player;
    }
}
