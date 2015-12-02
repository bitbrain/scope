package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;

public final class Styles {

    public static final Label.LabelStyle LABEL_DEBUG = new Label.LabelStyle();

    public static void init() {
        LABEL_DEBUG.font = AssetManager.getFont(Assets.Fonts.DEBUG_20);
        LABEL_DEBUG.fontColor = Color.WHITE.cpy();
    }
}
