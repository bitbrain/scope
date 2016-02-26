package nl.fontys.scope.core;

import java.util.Collection;
import java.util.HashMap;

import nl.fontys.scope.object.GameObject;

public class PlayerManager {

    private static Player current;

    private HashMap<String, Player> players = new HashMap<String, Player>();

    private HashMap<String, Player> shipToPlayers = new HashMap<String, Player>();

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
        return shipToPlayers.get(ship.getId());
    }

    public Player addPlayer() {
        return addPlayer(null, null);
    }

    public void removePlayer(String playerId) {
        if (players.containsKey(playerId)) {
            world.remove(players.get(playerId).getShip());
            players.remove(playerId);
            shipToPlayers.remove(playerId);
        }
    }

    public Player addPlayer(String clientId, String shipId) {
        Player player = new Player(world, clientId, shipId);
        players.put(player.getId(), player);
        player.setNumber(players.size());
        shipToPlayers.put(player.getShip().getId(), player);
        return player;
    }

    public Player  getPlayerById(String playerId) {
        return players.get(playerId);
    }
}
