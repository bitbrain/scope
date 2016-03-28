package nl.fontys.scope;

public interface Config {

    // Name of the application
    String APP_NAME = "scope";

    // Version of the application
    String APP_VERSION = "0.9.3";

    // console string of the debug statement
    String FLAG_DEBUG = "-debug";

    boolean NETWORKING_ENABLED = true;

    // Set to false if game should start not in fullscreen mode
    boolean AUTO_FULLSCREEN = false;

    // Set to false to disable audio
    boolean MUSIC_ENABLED = false;

    // Maximum distance for panning
    float MAX_PAN_DISTANCE = 50f;

    // Maximum distance for volume
    float MAX_VOLUME_DISTANCE = 100f;

    // Maximum energy speed
    float MAX_ENERGY_SPEED = 28f;

    // Default energy count
    int ENERGY_COUNT = 25;

    // Arena radius
    float ARENA_RADIUS = 140;

    // Arena radius (outer)
    float ARENA_OUTER_RADIUS = 100;

    // Soft gap of focus (100% approach)
    int FOCUS_SOFT_GAP = 16;

    // Points player gets per energy
    int POINTS_PER_ENERGY = 18;

    // Points to win the game
    int POINT_GAP = 500;

    // Number of munitions per energy consumption
    int MUNITION_GAP = 5;

    // Width of a menu button
    float MENU_BUTTON_WIDTH = 300f;

    // Height of a menu button
    float MENU_BUTTON_HEIGHT = 90f;

    // Padding between menu buttons
    float MENU_PADDING = 10f;

    // Initial alpha of menu
    float MENU_ALPHA = 0.5f;

    // Bar padding
    float UI_BAR_PADDING = 4f;

    // Offset of tooltips (vertical)
    float TOOLTIP_VERTICAL_OFFSET = 75f;

    // Margin of UI shadows
    float UI_SHADOW_MARGIN = 64f;

    // High quality rendering
    boolean HIGH_QUALITY = true;

    // The IP the game client should connect to
    String SERVER_IP = "127.0.0.1";

    // Timeout for server connection in miliseconds
    int SERVER_TIMEOUT_MS = 5000;

    // UDP port of the server
    int SERVER_UDP_PORT = 54555;

    // TCP port of the server
    int SERVER_TCP_PORT = 54777;

    // The maximum client size for a single game
    int MAX_CLIENT_SIZE = 2;
}
