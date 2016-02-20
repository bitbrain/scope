package nl.fontys.scope.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miguel on 20.02.16.
 */
public class CommandHandler {

    private final Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<String, Command>();
    }

    public void register(String identifier, Command command) {
        commands.put(identifier, command);
    }

    public void handle(String commandString) {

        String[] args = commandString.split(" ");

        Command command = commands.get(args[0]);

        if (command != null) {
            command.handle(reduceArgs(args));
        } else {
            System.out.println("Command '" + commandString + "' not found. Type 'help' for help!");
        }
    }

    public int size() {
        return commands.size();
    }

    public boolean isEmpty() {
        return commands.isEmpty();
    }


    static String[] reduceArgs(String[] args) {
        if (args.length <= 1)
            return new String[0];

        return Arrays.copyOfRange(args, 1, args.length);
    }
}
