package nl.fontys.scope.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.List;

import nl.fontys.scope.i18n.Bundle;

/**
 * Handles tooltip queueing
 */
public class TooltipQueue {

    private class Config {
        public String messageId;
        Label.LabelStyle style;

        public Config(String messageId, Label.LabelStyle style) {
            this.messageId = messageId;
            this.style = style;
        }
    }

    private List<Config> messages;

    public TooltipQueue() {
        this.messages = new ArrayList<Config>();
    }

    public TooltipQueue add(String messageId, Label.LabelStyle style) {
        messages.add(new Config(messageId, style));
        return this;
    }

    public void act() {
        Tooltip tooltip = Tooltip.getInstance();
        if (tooltip.isIdle() && !messages.isEmpty()) {
            Config config = messages.remove(0);
            tooltip.create(config.style, Bundle.general.get(config.messageId));
        }
    }
}
