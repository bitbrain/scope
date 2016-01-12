package nl.fontys.scope.controls;

import java.util.HashMap;
import java.util.Map;

public class ActionMapper {

    private Map<Integer, MoveableAction> actionMapping;

    public ActionMapper() {
        actionMapping = new HashMap<Integer, MoveableAction>();
    }
}
