package nl.fontys.scope.ui;

import net.engio.mbassy.listener.Handler;

import javax.tools.Tool;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
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
            if (player != null) {
                if (player.isCurrentPlayer()) {
                    tooltip.create(Styles.LABEL_CAPTION, Bundle.general.get(Messages.TOOLTIP_DESTROYED));
                } else {
                    tooltip.create(Styles.LABEL_CAPTION, Bundle.general.get(Messages.TOOLTIP_DESTROYED_OTHER));
                }
            }
        } else if (event.isTypeOf(EventType.ON_SHOT)) {
            GameObject object = (GameObject) event.getSecondaryParam(0);
            Player player = playerManager.getPlayerByShip(object);
            if (player != null) {
                if (player.isCurrentPlayer() && player.getHealth() < 0.2f) {
                    tooltip.create(Styles.LABEL_CAPTION,  Bundle.general.get(Messages.TOOLTIP_ENERGY_WARNING));
                }
            }
        }
    }
}
