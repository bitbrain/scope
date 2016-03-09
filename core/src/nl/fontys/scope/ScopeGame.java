package nl.fontys.scope;

import com.badlogic.gdx.Game;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.screens.AbstractScreen;
import nl.fontys.scope.screens.LoadingAssetsScreen;
import nl.fontys.scope.screens.ScreenHandler;

public class ScopeGame extends Game {

    private String[] args;

    public ScopeGame(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
	public void create() {
        ScreenHandler.setFull();
        setScreen(new LoadingAssetsScreen(this));
	}

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
        if (AbstractScreen.uiBuffer != null) {
            AbstractScreen.uiBuffer.dispose();
        }
    }
}
