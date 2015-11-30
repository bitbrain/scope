package nl.fontys.scope.assets;

import com.badlogic.gdx.graphics.Texture;

/**
 * Contains asset definitions
 */
public final class Assets {

    public enum Fonts {

        OPENSANS_MEDIUM_32("fonts/opensans-medium-32.fnt");

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

        CUBEMAP_SPACE_1("cubemaps/space1.png");

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
        RING("models/ring/ring.obj");

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

        STARSURFER("music/starsurfer.ogg");

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
        TEST("");

        private String path;

        Sounds(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
