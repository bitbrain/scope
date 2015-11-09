package nl.fontys.scope;

import com.badlogic.gdx.Game;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.screens.ModelLoadingScreen;

public class ScopeGame extends Game {
	@Override
	public void create() {
		AssetManager.init();
		setScreen(new ModelLoadingScreen(AssetManager.getModel(Assets.Models.CRUISER)));
	}
}
