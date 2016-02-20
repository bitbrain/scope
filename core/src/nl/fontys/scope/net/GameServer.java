package nl.fontys.scope.net;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Game server class (standalone) which handles all the network logic
 */
public class GameServer {

    public static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

    private AtomicBoolean running = new AtomicBoolean(false);

    private GameInstanceManager instanceManager;

    private ConnectionManager connectionManager;

    public GameServer() {
        instanceManager = new GameInstanceManager();
        connectionManager = new ConnectionManager();
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
        // TODO
        if (command.equals("stop")) {
            stop();
        } else {
            LOGGER.warning("Command '" + command + "' not recognized.");
        }
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
