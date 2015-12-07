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
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new ScopeGame(arg), config);
	}
}
