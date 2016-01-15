package nl.fontys.scope.networking;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.networking.broadCasts.GameObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.MovementBroadCast;
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
            ServerGame game = games.get(999L);
            connection.sendTCP(new GameStartedCheckResponse(game.isStarted()));
            //if (games.containsKey(((GameStartedCheckRequest) object).gameID)) {
            //    connection.sendTCP(new GameStartedCheckResponse(games.get(((GameStartedCheckRequest) object).gameID).isStarted()));
           // }
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
            //sendToAllPlayersOfGameTCP(((PlayerBroadCast) object).getClientID(), ((PlayerBroadCast) object).getGameID(), object);
            sendToAllPlayersOfGameTCP(((PlayerBroadCast) object).getClientID(), 999L, object);
        }
    }

    private void handleGameObjectBroadCast(Connection connection, Object object){
        //long gameID = ((GameObjectBroadCast) object).getGameID();
        long gameID = 999;
        long clientID = ((GameObjectBroadCast) object).getClientID();
        //System.out.println("Broadcasting Event for game: " + gameID + " clientID " + clientID);

        if (object instanceof MovementBroadCast) {
            sendToAllPlayersOfGameUDP(clientID, gameID, object);
        }
        else {
            sendToAllPlayersOfGameTCP(clientID, gameID, object);
        }
    }

    private void handleJoinRequest(Connection connection, Object object){
        System.out.println("Joining Game " + ((JoinRequest) object).getGameID());
        long gameID = 999;
        //ServerGame game = games.get(((JoinRequest) object).getGameID());
        ServerGame game = games.get(gameID);

        //JoinedResponse response = new JoinedResponse(game.getID());
        JoinedResponse response = new JoinedResponse(gameID);

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
        System.out.println("UDP Broadcast from " + clientID);
        if (game != null && game.isStarted()) {
            for (Map.Entry<Long, Connection> entry : game.getPlayers().entrySet()) {
                if (entry.getKey() != clientID){
                 entry.getValue().sendUDP(message);
                }
            }
        }
    }

    public void sendToAllPlayersOfGameTCP(long clientID, long gameID, Object message ){
        System.out.println("TCP Broadcast from " + clientID);
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
        sendToAllPlayersOfGameTCP(-1L, game.getID(), new GameStartedResponse());
    }

}
