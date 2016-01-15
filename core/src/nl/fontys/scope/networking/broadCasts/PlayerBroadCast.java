package nl.fontys.scope.networking.broadCasts;

import nl.fontys.scope.core.Player;

public class PlayerBroadCast extends BroadcastRequest {

    private Player payer;
    private int Value;
    private String EventType;

    public PlayerBroadCast() {

    }

    public PlayerBroadCast(long clientID, long timeStamp, long gameID, Player payer, int value, String eventType) {
        super(clientID,timeStamp, gameID);
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
