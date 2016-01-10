package nl.fontys.scope.networking.requests;

public class AbstractRequest{

    long clientID;

    public long getClientID() {
        return clientID;
    }

    public AbstractRequest(){}

    public AbstractRequest(long clientID) {
        this.clientID = clientID;
    }
}
