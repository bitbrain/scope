package nl.fontys.scope.assets;

import com.badlogic.gdx.graphics.Texture;

public final class Assets {

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

    public enum Models {

        CRUISER("models/cruiser/cruiser.obj");

        private String path;

        Models(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
