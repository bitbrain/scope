package nl.fontys.scope.networking.broadCasts;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.object.GameObject;

/**
 * Created by Jan Kerkenhoff on 16/12/15.
 */
public class PlayerBroadCast extends BroadcastRequest {

    private Player payer;
    private int Value;
    private String EventType;

    public PlayerBroadCast(long timeStamp, long gameID, Player payer, int value, String eventType) {
        super(timeStamp, gameID);
        this.payer = payer;
        Value = value;
        EventType = eventType;
    }

    public Player getPayer() {
        return payer;
    }

    public int getValue() {
        return Value;
    }

    public String getEventType() {
        return EventType;
    }
}
