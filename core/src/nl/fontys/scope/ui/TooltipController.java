package nl.fontys.scope.ui;

import net.engio.mbassy.listener.Handler;

import aurelienribon.tweenengine.Tween;
import nl.fontys.scope.Config;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.tweens.ZoomerShaderTween;

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
                    show(Messages.TOOLTIP_DESTROYED);
                } else {
                    tooltip.create(Config.TOOLTIP_VERTICAL_OFFSET, Styles.LABEL_CAPTION, "BOOOOM!");
                    tooltip.create(Config.TOOLTIP_VERTICAL_OFFSET / 3f, Styles.LABEL_PLAYER_NAME, Bundle.general.format(Messages.TOOLTIP_DESTROYED_OTHER, player.getNumber()));
                }
            }
        } else if (event.isTypeOf(EventType.ON_SHOT)) {
            GameObject object = (GameObject) event.getSecondaryParam(0);
            Player player = playerManager.getPlayerByShip(object);
            if (player != null) {
                if (player.isCurrentPlayer() && player.getHealth() < 0.2f) {
                    show(Messages.TOOLTIP_ENERGY_WARNING);
                }
            }
        } else if (event.isTypeOf(EventType.POINTS_GAINED)) {
            Player player = (Player) event.getPrimaryParam();
            final int PROGRESS = Math.round(player.getGameProgress() * 100f);
            if (player.isCurrentPlayer()) {
                show(Messages.TOOLTIP_POINTS_GAINED, PROGRESS);
            } else {
                show(Messages.TOOLTIP_POINTS_GAINED_OTHER, PROGRESS, player.getNumber());
            }
        }
    }

    private void show(String key, Object ... args) {
        tooltip.create(Config.TOOLTIP_VERTICAL_OFFSET, Styles.LABEL_CAPTION, Bundle.general.format(key, args));
    }
}
