package nl.fontys.scope.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.fontys.scope.ScopeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080;
		config.vSyncEnabled = true;
		config.useHDPI = true;
		config.samples = 8;
		new LwjglApplication(new ScopeGame(), config);
	}
}
