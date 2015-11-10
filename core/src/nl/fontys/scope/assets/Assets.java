package nl.fontys.scope.assets;

import com.badlogic.gdx.graphics.Texture;

/**
 * Contains asset definitions
 */
public final class Assets {

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
        RING("models/ring/ring.obj");

        private String path;

        Models(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
