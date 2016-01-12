package nl.fontys.scope.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.ExitHandler;

public class KeyboardMenuSupport {

    private ButtonMenu menu;

    private ExitHandler exitHandler;

    public KeyboardMenuSupport(ButtonMenu menu, ExitHandler exitHandler) {
        this.menu = menu;
        this.exitHandler = exitHandler;
    }

    public void update(float delta) {
        if (menu != null) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                menu.checkNext();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                menu.checkPrevious();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                menu.clickChecked();
            }
        }
        if (exitHandler != null && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            exitHandler.exit();
        }
    }
}
