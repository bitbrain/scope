package nl.fontys.scope.net.commands;

import nl.fontys.scope.net.GameServer;
import nl.fontys.scope.util.Command;

public class StopCommand implements Command {

    private GameServer server;

    public StopCommand(GameServer server) {
        this.server = server;
    }

    @Override
    public void handle(String[] command) {
        this.server.stop();
    }
}
