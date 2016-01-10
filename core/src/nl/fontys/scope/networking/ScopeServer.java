package nl.fontys.scope.networking;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import nl.fontys.scope.core.Player;
import nl.fontys.scope.networking.broadCasts.CollisionBroadCast;
import nl.fontys.scope.networking.broadCasts.GameObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.MovementBroadCast;
import nl.fontys.scope.networking.broadCasts.ObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.PlayerBroadCast;
import nl.fontys.scope.networking.requests.GameCreateRequest;
import nl.fontys.scope.networking.requests.GameStartedCheckRequest;
import nl.fontys.scope.networking.requests.GetGamesRequest;
import nl.fontys.scope.networking.requests.JoinRequest;
import nl.fontys.scope.networking.responses.GameStartedCheckResponse;
import nl.fontys.scope.networking.responses.GameStartedResponse;
import nl.fontys.scope.networking.responses.GetGamesResponse;
import nl.fontys.scope.networking.responses.JoinedResponse;

import nl.fontys.scope.networking.serverobjects.ServerGame;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;

public class ScopeServer  extends  Listener implements Disposable {
    private Server server;

    private static int TcpKeepAliveTimeOut = 200000000;

    private HashMap<Long, ServerGame> games;

    public ScopeServer() {

        server = new Server();

        games = new HashMap<Long, ServerGame>();

        server.addListener(this);
        Kryo kryo = server.getKryo();
        ScopeNetworkingHelper.registerClasses(kryo);

        server.start();

        try {
            server.bind(ScopeNetworkingHelper.TCP_PORT, ScopeNetworkingHelper.UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof GameStartedCheckRequest){
            if (games.containsKey(((GameStartedCheckRequest) object).gameID)) {
                connection.sendTCP(new GameStartedCheckResponse(games.get(((GameStartedCheckRequest) object).gameID).isStarted()));
            }
        }

        if (object instanceof GameCreateRequest){
            handleCreateGameRequest(connection, object);
        }

        if (object instanceof JoinRequest) {
            handleJoinRequest(connection, object);
        }

        if (object instanceof GetGamesRequest) {
            handleGetGamesRequest(connection, object);
        }

        if (object instanceof GameObjectBroadCast) {
            handleGameObjectBroadCast(connection, object);
        }

        if (object instanceof PlayerBroadCast) {
            sendToAllPlayersOfGameTCP(((PlayerBroadCast) object).getClientID(), ((PlayerBroadCast) object).getGameID(), object);
        }
    }

    private void handleGameObjectBroadCast(Connection connection, Object object){
        long gameID = ((GameObjectBroadCast) object).getGameID();
        long clientID = ((GameObjectBroadCast) object).getClientID();
        System.out.println("Broadcasting Event for game: " + gameID);

        if (object instanceof MovementBroadCast) {
            sendToAllPlayersOfGameUDP(clientID, gameID, object);
        }
        else {
            sendToAllPlayersOfGameTCP(clientID, gameID, object);
        }
    }

    private void handleJoinRequest(Connection connection, Object object){
        System.out.println("Joining Game " + ((JoinRequest) object).getGameID());
        ServerGame game = games.get(((JoinRequest) object).getGameID());

        JoinedResponse response = new JoinedResponse(game.getID());

        if (game.getPlayers().size() < game.getPlayerCount()) {
            game.getPlayers().put(((JoinRequest) object).getClientID(), connection);

            if (game.getPlayers().size() == game.getPlayerCount()) {
                startGame(game);
            }
        }
        else
            response.setSuccessful(false);

        connection.sendTCP(response);
    }

    private void handleCreateGameRequest(Connection connection, Object object){
        ServerGame newGame = new ServerGame(((GameCreateRequest) object).getPlayerCount(), ((GameCreateRequest) object).getGameName());
        newGame.getPlayers().put(((GameCreateRequest) object).getClientID(), connection);

        JoinedResponse response = new JoinedResponse(newGame.getID());
        response.setSuccessful(true);
        System.out.println("Created Game " + newGame.getID());
        games.put(newGame.getID(), newGame);

        connection.sendTCP(response);
    }

    private void handleGetGamesRequest(Connection connection, Object object){
        GetGamesResponse response = new GetGamesResponse();
        HashMap<String, Long> gameNames = new HashMap<String, Long>();
        for (Map.Entry game : games.entrySet())
        {
            gameNames.put(((ServerGame) game.getValue()).getGameName(), (Long) game.getKey());
        }
        response.setGames(gameNames);
        connection.sendTCP(response);
    }

    public void sendToAllPlayersOfGameUDP(long clientID, long gameID, Object message ){
        ServerGame game = games.get(gameID);
        if (game != null && game.isStarted()) {
            for (Map.Entry<Long, Connection> entry : game.getPlayers().entrySet()) {
                if (entry.getKey() != clientID){
                 entry.getValue().sendUDP(message);
                }
            }
        }
    }

    public void sendToAllPlayersOfGameTCP(long clientID, long gameID, Object message ){
        System.out.println("Sending to Game " + gameID);
        ServerGame game = games.get(gameID);
        if (game != null && game.isStarted()) {
            for (Map.Entry<Long, Connection> entry : game.getPlayers().entrySet()) {
                if (entry.getKey() != clientID){
                    entry.getValue().sendTCP(message);
                }
            }

        }
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }

    @Override
    public void connected(Connection connection) {
        connection.setKeepAliveTCP(TcpKeepAliveTimeOut);
    }

    @Override
    public void disconnected(Connection connection) {
        //dispose();
    }

    @Override
    public void dispose() {
        server.close();
        server.stop();
    }

    private void startGame(ServerGame game){

        game.setStarted(true);
        System.out.println("Starting Game " + game.getID());
        sendToAllPlayersOfGameTCP(-1, game.getID(), new GameStartedResponse());
    }

}
