package nl.fontys.scope.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DebugWidget extends Table {

    private Label position;

    public DebugWidget() {
        setFillParent(true);
        position = new Label("Position: 0, 0, 0", Styles.LABEL_DEBUG);
        add(position);
    }
}
