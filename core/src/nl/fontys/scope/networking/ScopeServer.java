package nl.fontys.scope.networking;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.*;

import java.io.IOException;
import java.util.HashMap;


import nl.fontys.scope.networking.broadCasts.BroadcastRequest;
import nl.fontys.scope.networking.broadCasts.MovementBroadCast;
import nl.fontys.scope.networking.requests.GameCreateRequest;
import nl.fontys.scope.networking.requests.GetGamesRequest;
import nl.fontys.scope.networking.requests.JoinRequest;
import nl.fontys.scope.networking.responses.GetGamesResponse;
import nl.fontys.scope.networking.responses.JoinedResponse;
import nl.fontys.scope.networking.responses.MovementUpdate;
import nl.fontys.scope.networking.serverobjects.ServerGame;

public class ScopeServer  extends  Listener implements Disposable {
    private Server server;

    private static int TcpKeepAliveTimeOut = 2000;

    private HashMap<Long, ServerGame> games;

    public ScopeServer() {

        server = new Server();
        games = new HashMap<Long, ServerGame>();

        server.addListener(this);

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
            ServerGame newGame = new ServerGame(((GameCreateRequest) object).getPlayerCount());
            newGame.getPlayers().add(connection);

            JoinedResponse response = new JoinedResponse(newGame.getID());
            connection.sendTCP(response);

            games.put(newGame.getID(), newGame);

        }

        if (object instanceof JoinRequest) {

            ServerGame game = games.get(((JoinRequest) object).getGameID());

            JoinedResponse response = new JoinedResponse(game.getID());

            if (game.getPlayers().size() == 2) {
                game.getPlayers().add(connection);

                StartGame(game);
            }
            else
                response.setSuccessful(false);

            connection.sendTCP(response);

        }

        if (object instanceof GetGamesRequest) {
            GetGamesResponse response = new GetGamesResponse();
            response.setGames(games);
            connection.sendTCP(response);
        }

        if (object instanceof MovementBroadCast) {
            ServerGame game = games.get(((MovementBroadCast) object).getGameID());
            MovementUpdate response = new MovementUpdate((MovementBroadCast) object);
            for (Connection con: game.getPlayers()) con.sendUDP(response);

        }

        if (object instanceof BroadcastRequest) {

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

    private void StartGame(ServerGame game){


    }


}
