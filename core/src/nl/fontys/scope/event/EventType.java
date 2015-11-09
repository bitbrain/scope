package nl.fontys.scope.event;

/**
 * Contains event types
 */
public class EventType {

    // Is called after an object gets removed from the world (param: GameObject)
    public static final String OBJECT_REMOVED = "object_removed";

    // Is called before rendering a game object (param: GameObject, float)
    public static final String OBJECT_UPDATED = "object_updated";
}
