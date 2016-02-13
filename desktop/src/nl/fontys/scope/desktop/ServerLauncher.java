package nl.fontys.scope.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import nl.fontys.scope.net.GameServer;

public class ServerLauncher {

    private GameServer server;

    private LineReader reader;

    private ServerLauncher() {
        reader = new LineReader();
        server = new GameServer();
    }

    public void init() {
        server.start();
        while (server.isRunning()) {
            try {
                String command = reader.readLine();
                if (command != null && !command.trim().isEmpty()) {
                    server.command(command);
                }
            } catch (IOException e) {
                System.out.println("Could not pass command!");
            }
        }
    }

    public static void main(String[] args) {
        ServerLauncher launcher = new ServerLauncher();
        launcher.init();
    }

    private class LineReader {

        BufferedReader br;

        private LineReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String readLine() throws IOException {
            return br.readLine();
        }
    }
}
