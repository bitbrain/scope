package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.util.Colors;

public final class Styles {

    public static final Label.LabelStyle LABEL_DEBUG = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_CREDITS = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_VERSION = new Label.LabelStyle();

    public static final TextButton.TextButtonStyle BUTTON_MENU = new TextButton.TextButtonStyle();

    public static void init() {
        LABEL_DEBUG.font = AssetManager.getFont(Assets.Fonts.DEBUG_20);
        LABEL_DEBUG.fontColor = Color.WHITE.cpy();

        LABEL_CREDITS.font = AssetManager.getFont(Assets.Fonts.OPENSANS_SMALL_16);
        Color color = Colors.PRIMARY.cpy();
        color.a = 0.5f;
        LABEL_CREDITS.fontColor = color;

        LABEL_VERSION.font = AssetManager.getFont(Assets.Fonts.OPENSANS_SMALL_16);
        color = Colors.PRIMARY_ADDITION.cpy();
        color.a = 0.8f;
        LABEL_VERSION.fontColor = color;

        BUTTON_MENU.font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        BUTTON_MENU.fontColor = Colors.PRIMARY.cpy();
        BUTTON_MENU.up = new NinePatchDrawable(GraphicsFactory.createNinePatch(Assets.Textures.BUTTON, 30));
        BUTTON_MENU.down = new NinePatchDrawable(GraphicsFactory.createNinePatch(Assets.Textures.BUTTON, 30));
    }
}
