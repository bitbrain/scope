package nl.fontys.scope.networking;

import com.esotericsoftware.kryo.Kryo;

import nl.fontys.scope.networking.broadCasts.BroadcastRequest;
import nl.fontys.scope.networking.broadCasts.CollisionBroadCast;
import nl.fontys.scope.networking.broadCasts.GameObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.MovementBroadCast;
import nl.fontys.scope.networking.broadCasts.ObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.PlayerBroadCast;
import nl.fontys.scope.networking.requests.GameCreateRequest;
import nl.fontys.scope.networking.requests.GetGamesRequest;
import nl.fontys.scope.networking.requests.JoinRequest;
import nl.fontys.scope.networking.responses.GetGamesResponse;
import nl.fontys.scope.networking.responses.JoinedResponse;

public abstract class ScopeNetworkingHelper {

    public static void registerClasses(Kryo kyro){

        // Broadcasts
        kyro.register(BroadcastRequest.class);
        kyro.register(CollisionBroadCast.class);
        kyro.register(GameObjectBroadCast.class);
        kyro.register(MovementBroadCast.class);
        kyro.register(ObjectBroadCast.class);
        kyro.register(PlayerBroadCast.class);

        // Request
        kyro.register(GameCreateRequest.class);
        kyro.register(GetGamesRequest.class);
        kyro.register(JoinRequest.class);

        // Responses
        kyro.register(GetGamesResponse.class);
        kyro.register(JoinedResponse.class);
    }
}
