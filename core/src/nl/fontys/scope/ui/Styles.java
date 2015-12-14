package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.util.Colors;

public final class Styles {

    public static final Label.LabelStyle LABEL_DEBUG = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_CREDITS = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_VERSION = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_CAPTION = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_DESCRIPTION = new Label.LabelStyle();

    public static final TextButton.TextButtonStyle BUTTON_MENU = new TextButton.TextButtonStyle();

    public static final TextField.TextFieldStyle TEXTFIELD_FORM = new TextField.TextFieldStyle();

    public static void init() {
        LABEL_DEBUG.font = AssetManager.getFont(Assets.Fonts.DEBUG_20);
        LABEL_DEBUG.fontColor = Color.WHITE.cpy();

        LABEL_CREDITS.font = AssetManager.getFont(Assets.Fonts.OPENSANS_SMALL_16);
        Color color = Colors.PRIMARY.cpy();
        color.a = 0.5f;
        LABEL_CREDITS.fontColor = color;

        LABEL_VERSION.font = AssetManager.getFont(Assets.Fonts.OPENSANS_SMALL_16);
        color = Colors.SECONDARY.cpy();
        color.a = 0.8f;
        LABEL_VERSION.fontColor = color;

        LABEL_CAPTION.font = AssetManager.getFont(Assets.Fonts.OPENSANS_LARGE_64);
        LABEL_CAPTION.fontColor = Colors.PRIMARY.cpy();

        LABEL_DESCRIPTION.font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        LABEL_DESCRIPTION.fontColor = Colors.SECONDARY.cpy();

        BUTTON_MENU.font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        BUTTON_MENU.fontColor = Colors.PRIMARY.cpy();
        BUTTON_MENU.overFontColor = Colors.ACTIVE.cpy();
        BUTTON_MENU.up = new NinePatchDrawable(GraphicsFactory.createNinePatch(Assets.Textures.BUTTON, 40));
        BUTTON_MENU.down = new NinePatchDrawable(GraphicsFactory.createNinePatch(Assets.Textures.BUTTON_ACTIVE, 40));
        BUTTON_MENU.over = new NinePatchDrawable(GraphicsFactory.createNinePatch(Assets.Textures.BUTTON_ACTIVE, 40));

        TEXTFIELD_FORM.font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        TEXTFIELD_FORM.messageFont = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        TEXTFIELD_FORM.fontColor = Colors.PRIMARY;
        TEXTFIELD_FORM.focusedFontColor = Color.ORANGE;
        TEXTFIELD_FORM.messageFontColor = Colors.SECONDARY;
    }
}
