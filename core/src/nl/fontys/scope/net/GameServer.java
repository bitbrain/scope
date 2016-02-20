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

    public GameServer() {
        instanceManager = new GameInstanceManager();
    }

    /**
     * Starts the server (initialization)
     */
    public void start() {
        LOGGER.info("Starting game server..");
        // TODO
        running.set(true);
        LOGGER.info("Started!");
    }

    /**
     * Passes a simple command to the server
     */
    public void command(String command) {
        LOGGER.info("Passing command: '" + command + "'");
        // TODO
        if (command.equals("stop")) {
            stop();
        }
    }

    /**
     * Stops the server (shutdown)
     */
    public void stop() {
        LOGGER.info("Stopping game server..");
        // TODO
        running.set(false);
        LOGGER.info("Stopped!");
    }

    public boolean isRunning() {
        return running.get();
    }
}
