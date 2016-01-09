package nl.fontys.scope.networking.responses;

import nl.fontys.scope.networking.requests.GameStartedCheckRequest;

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
