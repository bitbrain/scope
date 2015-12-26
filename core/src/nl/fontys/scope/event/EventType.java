package nl.fontys.scope.event;

/**
 * Contains event types
 */
public class EventType {

    // Is called after an object gets removed from the world (param: GameObject)
    public static final String OBJECT_REMOVED = "object_removed";

    // Is called on an object collision (param: GameObject, GameObject)
    public static final String COLLISION = "collision";

    // Is called on an full object collision (containment) (param: GameObject, GameObject)
    public static final String COLLISION_FULL = "collision_full";

    // Is called whenever a player gets points (param: Player, int)
    public static final String POINTS_GAINED = "points_gained";

    // Is called whenever a player drops energy (param: Player, int)
    public static final String ENERGY_DROPPED = "energy_dropped";

    // Is called whenever a player gets destroyed (param: GameObject)
    public static final String PLAYER_SHIP_DESTROYED = "player_ship_destroyed";

    // Is called whenever a player dies (health <= 0) (param: PLayer)
    public static final String PLAYER_DEAD = "player_dead";
}
