package nl.fontys.scope.controls;

import com.badlogic.gdx.controllers.PovDirection;

import nl.fontys.scope.ui.ButtonMenu;

public class XboxMenuControllerSupport extends XBoxControllerSupport {

    private ButtonMenu menu;

    public XboxMenuControllerSupport(ButtonMenu menu) {
        super(null);
        this.menu = menu;
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onButtonClick(int code, float strength) {
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

    @Override
    protected void povMoved(PovDirection direction) {
        if (direction == PovDirection.north) {
            menu.checkPrevious();
        } else if (direction == PovDirection.south) {
            menu.checkNext();
        }
    }
}
