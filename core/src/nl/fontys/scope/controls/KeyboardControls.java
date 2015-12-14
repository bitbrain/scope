package nl.fontys.scope.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;

public class KeyboardControls {

    private Moveable moveable;

    public KeyboardControls(Moveable moveable) {
        this.moveable = moveable;
    }

    public void update(float delta) {
        Player current = PlayerManager.getCurrent();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveable.moveForward();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveable.moveBack();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveable.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveable.moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            moveable.moveUp();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            moveable.moveDown();
        }
        if (Gdx.input.isTouched()) {
            current.getWeapon().shoot();
        }
    }
}
