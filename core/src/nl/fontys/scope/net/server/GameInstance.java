package nl.fontys.scope.net.server;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.core.Player;

public class GameInstance {

    private String name;

    private Map<String, Player> players;

    public GameInstance(String name) {
        this.name = name;
        this.players = new HashMap<String, Player>();
    }

    public String getName() {
        return name;
    }

    public void init() {
        // TODO
    }

    public void close() {
        // TODO
    }
}
