package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;

import nl.fontys.scope.Config;

public final class ScreenHandler {

    public static void setFull() {
        if (Config.AUTO_FULLSCREEN) {
            Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

    }
}
