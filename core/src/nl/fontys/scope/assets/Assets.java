package nl.fontys.scope.assets;

/**
 * Contains asset definitions
 */
public final class Assets {

    public enum Fonts {

        DEBUG_20("fonts/debug-14.fnt"),
        OPENSANS_SMALL_16("fonts/opensans-small-16.fnt"),
        OPENSANS_MEDIUM_32("fonts/opensans-medium-32.fnt"),
        OPENSANS_LARGE_64("fonts/opensans-large-64.fnt");

        private String path;

        Fonts(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains texture definitions
     */
    public enum Textures {

        CUBEMAP_SPACE_1("cubemaps/space1.png"),
        BUTTON("images/button.9.png"),
        FOCUS("images/focus.9.png"),
        FOCUS_TARGET("images/focus-target.9.png"),
        TEXTFIELD("images/textfield.9.png"),
        BAR_SMALL("images/bar-small.9.png"),
        FILL("images/fill.9.png"),
        BORDER("images/border.9.png"),
        SHADOW("images/shadow.9.png"),
        LOGO("images/logo.png");

        private String path;

        Textures(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains model definitions
     */
    public enum Models {

        CRUISER("models/cruiser/cruiser.obj"),
        PLANET("models/planet_01/planet.obj"),
        ENERGY("models/energy/energy.obj"),
        SHOT("models/shot/shot.obj");

        private String path;

        Models(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains music definitions
     */
    public enum Musics {

        MAIN_THEME("music/scope_theme.ogg");

        private String path;

        Musics(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains sound definitions
     */
    public enum Sounds {

        MENU_SELECT("sounds/select-01.mp3"),
        MENU_HOVER("sounds/rollover-00.mp3"),
        EXPLOSION("sounds/explosion.mp3"),
        LASER("sounds/laser.ogg");

        private String path;

        Sounds(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    /**
     * Contains particle effect definitions
     */
    public enum ParticleEffects {
        EXPLOSION("particles/explosion.p"),
        ENERGY("particles/energy.p"),
        POWER("particles/power.p"),
        SPHERE("particles/sphere.p"),
        HIT("particles/hit.p");

        private String path;

        ParticleEffects(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
