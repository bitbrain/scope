package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.PovDirection;

import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.ExitHandler;

public class XboxMenuControllerSupport extends XBoxControllerSupport {

    private ButtonMenu menu;

    private ExitHandler exitHandler;

    public XboxMenuControllerSupport(ButtonMenu menu, ExitHandler exitHandler) {
        super(null);
        this.menu = menu;
        this.exitHandler = exitHandler;
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onButtonClick(int code, float strength) {
        if (menu != null) {
            if (strength != 0f && (code == Buttons.RIGHT_STICK_CODE_Y || code == Buttons.LEFT_STICK_CODE_Y)) {
                if (strength == 1f) {
                    menu.checkNext();
                } else if (strength == -1f) {
                    menu.checkPrevious();
                }
            } else if (code == Buttons.A) {
                menu.clickChecked();
            }
        }
        if (strength == 0f && code == Buttons.B && exitHandler != null) {
            exitHandler.exit();
        }
    }

    @Override
    protected void povMoved(PovDirection direction) {
        if (menu == null) {
            return;
        }
        if (direction == PovDirection.north) {
            menu.checkPrevious();
        } else if (direction == PovDirection.south) {
            menu.checkNext();
        }
    }
}
