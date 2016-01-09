package nl.fontys.scope;

public class Config {

    // Name of the application
    public static final String APP_NAME = "scope";

    // Version of the application
    public static final String APP_VERSION = "0.6.7";

    // console string of the debug statement
    public static final String FLAG_DEBUG = "-debug";

    // Set to false if game should start not in fullscreen mode
    public static final boolean AUTO_FULLSCREEN = false;

    // Maximum distance for panning
    public static final float MAX_PAN_DISTANCE = 50f;

    // Maximum distance for volume
    public static final float MAX_VOLUME_DISTANCE = 100f;

    // Maximum energy speed
    public static final float MAX_ENERGY_SPEED = 28f;

    // Maximum ship speed
    public static final float MAX_SHIP_SPEED = 2.4f;

    // Default energy count
    public static final int ENERGY_COUNT = 25;

    // Arena radius
    public static final float ARENA_RADIUS = 140;

    // Arena radius (outer)
    public static final float ARENA_OUTER_RADIUS = 100;

    // Soft gap of focus (100% approach)
    public static final int FOCUS_SOFT_GAP = 16;

    // Points player gets per energy
    public static final int POINTS_PER_ENERGY = 18;

    // Points to win the game
    public static final int POINT_GAP = 500;

    // Number of munitions per energy consumption
    public static final int MUNITION_GAP = 5;

    // Width of a menu button
    public static final float MENU_BUTTON_WIDTH = 300f;

    // Height of a menu button
    public static final float MENU_BUTTON_HEIGHT = 90f;

    // Padding between menu buttons
    public static final float MENU_PADDING = 10f;

    // Initial alpha of menu
    public static final float MENU_ALPHA = 0.5f;

    // Bar padding
    public static final float UI_BAR_PADDING = 4f;

    // Offset of tooltips (vertical)
    public static final float TOOLTIP_VERTICAL_OFFSET = 75f;

    // Margin of UI shadows
    public static final float UI_SHADOW_MARGIN = 64f;

    // High quality rendering
    public static final boolean HIGH_QUALITY = true;
}
