package nl.fontys.scope.event;

/**
 * Contains event types
 */
public class EventType {

    // Is called after an object gets removed from the world (param: GameObject)
    public static final String OBJECT_REMOVED = "object_removed";

    // Is called on an object collision (param: GameObject, GameObject)
    public static final String COLLISION = "collision";
}
