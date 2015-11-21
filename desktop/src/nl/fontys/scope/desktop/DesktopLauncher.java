package nl.fontys.scope.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.fontys.scope.ScopeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		config.vSyncEnabled = true;
		config.useHDPI = true;
		new LwjglApplication(new ScopeGame(), config);
	}
}
