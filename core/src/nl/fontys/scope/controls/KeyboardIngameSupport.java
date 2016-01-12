package nl.fontys.scope.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;

public class KeyboardIngameSupport {

    private Moveable moveable;

    public KeyboardIngameSupport(Moveable moveable) {
        this.moveable = moveable;
    }

    public void update(float delta) {
        Player current = PlayerManager.getCurrent();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveable.rotate(0f, 0f, -1f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveable.rotate(0f, 0f, 1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveable.rotate(-1f, 0f, 0f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveable.rotate(1f, 0f, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            moveable.shoot();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
           moveable.boost(1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) {
            moveable.boost(-1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveable.rise(1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveable.rise(-1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveable.rotate(0f, 1f, 1f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveable.rotate(0f, -1f, 1f);
        }

    }
}
