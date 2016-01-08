package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;

import nl.fontys.scope.Config;

public final class ScreenHandler {

    public static void setFull() {
        Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
        if (Config.AUTO_FULLSCREEN) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

    }
}
