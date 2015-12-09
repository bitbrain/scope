package nl.fontys.scope.networking;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.*;

import java.io.IOException;
import java.util.HashMap;


import nl.fontys.scope.networking.broadCasts.GameObjectBroadCast;
import nl.fontys.scope.networking.broadCasts.MovementBroadCast;
import nl.fontys.scope.networking.broadCasts.PlayerBroadCast;
import nl.fontys.scope.networking.requests.GameCreateRequest;
import nl.fontys.scope.networking.requests.GetGamesRequest;
import nl.fontys.scope.networking.requests.JoinRequest;
import nl.fontys.scope.networking.responses.GameStartedResponse;
import nl.fontys.scope.networking.responses.GetGamesResponse;
import nl.fontys.scope.networking.responses.JoinedResponse;

import nl.fontys.scope.networking.serverobjects.ServerGame;

public class ScopeServer  extends  Listener implements Disposable {
    private Server server;

    private static int TcpKeepAliveTimeOut = 2000;

    private HashMap<Long, ServerGame> games;

    public ScopeServer() {

        server = new Server();

        games = new HashMap<Long, ServerGame>();

        server.addListener(this);

        ScopeNetworkingHelper.registerClasses(server.getKryo());

        server.start();

        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void received(Connection connection, Object object) {

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
            sendToAllPlayersOfGameTCP(((PlayerBroadCast) object).getGameID(), object);
        }
    }

    private void handleGameObjectBroadCast(Connection connection, Object object){
        long gameID = ((GameObjectBroadCast) object).getGameID();

        if (object instanceof MovementBroadCast) {
            sendToAllPlayersOfGameUDP(gameID, object);
        }
        else {
            sendToAllPlayersOfGameTCP(gameID, object);
        }
    }

    private void handleJoinRequest(Connection connection, Object object){
        ServerGame game = games.get(((JoinRequest) object).getGameID());

        JoinedResponse response = new JoinedResponse(game.getID());

        if (game.getPlayers().size() < game.getPlayerCount()) {
            game.getPlayers().add(connection);

            if (game.getPlayers().size() == game.getPlayerCount()) {
                startGame(game);
            }
        }
        else
            response.setSuccessful(false);

        connection.sendTCP(response);
    }

    private void handleCreateGameRequest(Connection connection, Object object){
        ServerGame newGame = new ServerGame(((GameCreateRequest) object).getPlayerCount());
        newGame.getPlayers().add(connection);

        JoinedResponse response = new JoinedResponse(newGame.getID());
        connection.sendTCP(response);

        games.put(newGame.getID(), newGame);
    }

    private void handleGetGamesRequest(Connection connection, Object object){
        GetGamesResponse response = new GetGamesResponse();
        response.setGames(games);
        connection.sendTCP(response);
    }

    public void sendToAllPlayersOfGameUDP(long gameID, Object message ){
        ServerGame game = games.get(gameID);
        if (game.isStarted()) {
            for (Connection con : game.getPlayers()) con.sendUDP(message);
        }
    }

    public void sendToAllPlayersOfGameTCP(long gameID, Object message ){
        ServerGame game = games.get(gameID);
        if (game.isStarted()) {
            for (Connection con : game.getPlayers()) con.sendTCP(message);
        }
    }

    @Override
    public void connected(Connection connection) {
        connection.setKeepAliveTCP(TcpKeepAliveTimeOut);
    }

    @Override
    public void disconnected(Connection connection) {
        dispose();
    }

    @Override
    public void dispose() {
        server.close();
        server.stop();
    }

    private void startGame(ServerGame game){

        game.setStarted(true);
        sendToAllPlayersOfGameTCP(game.getID(), new GameStartedResponse());
    }
}
