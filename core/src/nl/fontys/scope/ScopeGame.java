package nl.fontys.scope;

import com.badlogic.gdx.Game;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.screens.AbstractScreen;
import nl.fontys.scope.screens.IngameScreen;
import nl.fontys.scope.screens.LoadingScreen;

public class ScopeGame extends Game {
	@Override
	public void create() {
        setScreen(new LoadingScreen(this));
	}

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
