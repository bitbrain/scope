package nl.fontys.scope;

import com.badlogic.gdx.Game;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.screens.AbstractScreen;
import nl.fontys.scope.screens.IngameScreen;

public class ScopeGame extends Game {
	@Override
	public void create() {
		AssetManager.init();
		//setScreen(new ModelLoadingScreen(AssetManager.getModel(Assets.Models.CRUISER)));
        setScreen(new IngameScreen());
	}

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
