package nl.fontys.scope.ui;

import net.engio.mbassy.listener.Handler;

import javax.tools.Tool;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;

public class TooltipController {

    private Events events = Events.getInstance();

    private Tooltip tooltip = Tooltip.getInstance();

    private PlayerManager playerManager;

    public TooltipController(PlayerManager playerManager) {
        events.register(this);
        this.playerManager = playerManager;
    }

    @Handler
    public void onEvent(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.PLAYER_SHIP_DESTROYED)) {
            GameObject playerShip = (GameObject) event.getPrimaryParam();
            Player player = playerManager.getPlayerByShip(playerShip);
            if (player.isCurrentPlayer()) {
                tooltip.create(Styles.LABEL_CAPTION, "You got destroyed!");
            } else {
                tooltip.create(Styles.LABEL_CAPTION, "Player " + player.getNumber() + " got destroyed!");
            }
        }
    }
}
