package nl.fontys.scope;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import nl.fontys.scope.screens.ModelLoadingScreen;

public class ScopeGame extends Game {
	@Override
	public void create() {
		setScreen(new ModelLoadingScreen("models/ship/ship.obj"));
	}
}
