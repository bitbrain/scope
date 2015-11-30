package nl.fontys.scope.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.fontys.scope.ScopeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.useHDPI = true;
		config.fullscreen = true;
		config.width = 1366;
		config.height = 768;
		new LwjglApplication(new ScopeGame(), config);
	}
}
