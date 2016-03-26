package nl.fontys.scope.event;

/**
 * Contains event types
 */
public interface EventType {


    // Is called after all Players joinded a Game and the server
    // signaled everyone that the game started (param: none)

    String GAME_START = "game_start";

    // Is called after an object gets removed from the world (param: GameObject)
    String OBJECT_REMOVED = "object_removed";

    // Is called after an object gets created (param: GameObject)
    String OBJECT_CREATED = "object_created";

    // Is called on an object collision (param: GameObject, GameObject)
    String COLLISION = "collision";

    // Is called on an full object collision (containment) (param: GameObject, GameObject)
    String COLLISION_FULL = "collision_full";

    // Is called whenever a player gets points (param: Player, int)
    String POINTS_GAINED = "points_gained";

    // Is called whenever a player drops energy (param: Player, int)
    String ENERGY_DROPPED = "energy_dropped";

    // Is called when a player got shot (param: Vector3, GameObject, String)
    String ON_SHOT = "on_shot";

    // Is called whenever a player gets destroyed (param: GameObject)
    String PLAYER_SHIP_DESTROYED = "player_ship_destroyed";

    // Is called whenever a player dies (health <= 0) (param: PLayer)
    String PLAYER_DEAD = "player_dead";

    // Is called whenever a game is over on the client
    String GAME_OVER = "game_over";
}
