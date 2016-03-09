package nl.fontys.scope.net.server;

import com.esotericsoftware.kryonet.Server;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import nl.fontys.scope.net.commands.OpenGamesCommand;
import nl.fontys.scope.net.commands.StopCommand;
import nl.fontys.scope.util.CommandHandler;

/**
 * Game server class (standalone) which handles all the network logic
 */
public class GameServer {

    public static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

    private AtomicBoolean running = new AtomicBoolean(false);

    private GameInstanceManager instanceManager;

    private ConnectionManager connectionManager;

    private CommandHandler commandHandler;

    public GameServer() {
        Server server = new Server();
        instanceManager = new GameInstanceManager(server);
        connectionManager = new ConnectionManager(server, instanceManager);
        commandHandler = new CommandHandler();
        commandHandler.register("stop", new StopCommand(this));
        commandHandler.register("games", new OpenGamesCommand(instanceManager));
    }

    /**
     * Starts the server (initialization)
     */
    public void start() {
        LOGGER.info("Starting game server..");
        try {
            connectionManager.start();
            running.set(true);
            LOGGER.info("Started!");
        } catch (GameServerException e) {
            LOGGER.warning("Could not start connection manager: " + e.getMessage());
        }
    }

    /**
     * Passes a simple command to the server
     */
    public void command(String command) {
       commandHandler.handle(command);
    }

    /**
     * Stops the server (shutdown)
     */
    public void stop() {
        LOGGER.info("Stopping game server..");
        running.set(false);
        connectionManager.dispose();
        LOGGER.info("Stopped!");
    }

    public boolean isRunning() {
        return running.get();
    }
}
