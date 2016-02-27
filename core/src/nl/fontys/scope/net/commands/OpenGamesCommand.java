package nl.fontys.scope.net.commands;

import nl.fontys.scope.net.server.GameInstance;
import nl.fontys.scope.net.server.GameInstanceManager;
import nl.fontys.scope.util.Command;

public class OpenGamesCommand implements Command {

    private GameInstanceManager instanceManager;

    public OpenGamesCommand(GameInstanceManager instanceManager) {
        this.instanceManager = instanceManager;
    }

    @Override
    public void handle(String[] command) {
        System.out.println("Open instances: ");
        for (GameInstance instance : instanceManager.getCurrentInstances()) {
            System.out.println(instance.getName() + ": " + instance.getCurrentClientSize() + "/" + instance.getMaxClientSize());
        }
    }
}
