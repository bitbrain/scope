package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.util.Colors;

import static nl.fontys.scope.graphics.GraphicsFactory.createDrawable;

public final class Styles {

    public static final Label.LabelStyle LABEL_DEBUG = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_CREDITS = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_VERSION = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_CAPTION = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_DESCRIPTION = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_VALIDATION = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_PLAYER_NAME = new Label.LabelStyle();
    public static final Label.LabelStyle LABEL_FOCUS = new Label.LabelStyle();

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

        LABEL_DESCRIPTION.font = AssetManager.getFont(Assets.Fonts.OPENSANS_SMALL_16);
        LABEL_DESCRIPTION.fontColor = Colors.SECONDARY.cpy();

        LABEL_VALIDATION.font = AssetManager.getFont(Assets.Fonts.OPENSANS_SMALL_16);
        LABEL_VALIDATION.fontColor = Color.RED.cpy();

        LABEL_PLAYER_NAME.font = AssetManager.getFont(Assets.Fonts.OPENSANS_SMALL_16);
        LABEL_PLAYER_NAME.fontColor = Colors.UI.cpy();

        LABEL_FOCUS.font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        LABEL_FOCUS.fontColor = Colors.PRIMARY.cpy();

        BUTTON_MENU.font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        BUTTON_MENU.fontColor = Colors.PRIMARY.cpy();
        BUTTON_MENU.overFontColor = Colors.ACTIVE.cpy();
        Color disabledColor = Colors.PRIMARY.cpy();
        disabledColor.a = 0.4f;
        BUTTON_MENU.disabledFontColor = disabledColor;
        BUTTON_MENU.up = createDrawable(Assets.Textures.BUTTON, Colors.UI);
        BUTTON_MENU.down = createDrawable(Assets.Textures.BUTTON, Colors.lighten(Colors.ACTIVE, 1.5f));
        BUTTON_MENU.over = createDrawable(Assets.Textures.BUTTON, Colors.ACTIVE);
        BUTTON_MENU.disabled = createDrawable(Assets.Textures.BUTTON, Colors.trans(Colors.UI, 0.4f));

        TEXTFIELD_FORM.font = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        TEXTFIELD_FORM.messageFont = AssetManager.getFont(Assets.Fonts.OPENSANS_MEDIUM_32);
        TEXTFIELD_FORM.fontColor = Colors.lighten(Colors.PRIMARY, 0.5f);
        TEXTFIELD_FORM.focusedFontColor = Colors.PRIMARY.cpy();
        TEXTFIELD_FORM.messageFontColor = Colors.lighten(Colors.PRIMARY, 0.5f);
        TEXTFIELD_FORM.background = createDrawable(Assets.Textures.TEXTFIELD, Colors.trans(Colors.UI, 0.7f));
        TEXTFIELD_FORM.focusedBackground = createDrawable(Assets.Textures.TEXTFIELD, Colors.UI);
        TEXTFIELD_FORM.cursor = new SpriteDrawable(new Sprite(GraphicsFactory.createTexture(3, 16, Colors.SECONDARY)));
    }
}
