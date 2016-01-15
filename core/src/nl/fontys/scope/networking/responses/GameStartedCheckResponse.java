package nl.fontys.scope.networking.responses;

/**
 * Created by Jan Kerkenhoff on 07/01/16.
 */
public class GameStartedCheckResponse {

    public GameStartedCheckResponse(){}

    public GameStartedCheckResponse(boolean started){
        super();
        this.started = started;
    }

    public boolean started;
}
