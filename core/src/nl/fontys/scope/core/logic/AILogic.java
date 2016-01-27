package nl.fontys.scope.core.logic;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;

public class AILogic implements Logic {

    private Player player;

    private Events events  = Events.getInstance();

    public AILogic(Player player) {
        this.player = player;
        events.register(this);
    }

    @Override
    public void update(GameObject object, float delta) {
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {

    }
}
