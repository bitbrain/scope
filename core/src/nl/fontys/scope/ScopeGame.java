package nl.fontys.scope;

import com.badlogic.gdx.Game;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.networking.ScopeClient;
import nl.fontys.scope.networking.ScopeServer;
import nl.fontys.scope.screens.AbstractScreen;
import nl.fontys.scope.screens.LoadingAssetsScreen;
import nl.fontys.scope.screens.ScreenHandler;

public class ScopeGame extends Game {

    private String[] args;

    public ScopeGame(String[] args) {
        this.args = args;
    }

    private ScopeServer server;

    private ScopeClient client;

    public ScopeServer startServer() {
        if (server == null){
            server = new ScopeServer();
        }
        return server;
    }

    public String[] getArgs() {
        return args;
    }

    public ScopeClient getClient() {
        return client;
    }

    public void setClient(ScopeClient client) {
        this.client = client;
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
