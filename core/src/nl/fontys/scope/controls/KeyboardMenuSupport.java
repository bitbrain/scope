package nl.fontys.scope.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import nl.fontys.scope.ui.ButtonMenu;

public class KeyboardMenuSupport {

    private ButtonMenu menu;

    public KeyboardMenuSupport(ButtonMenu menu) {
        this.menu = menu;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            menu.checkNext();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            menu.checkPrevious();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            menu.clickChecked();
        }
    }
}
