package nl.fontys.scope.util;

import com.badlogic.gdx.graphics.Color;

public final class Colors {

    public static final Color PRIMARY = Color.valueOf("00ff5a");
    public static final Color SECONDARY = Color.valueOf("110417");

    public static Color lighten(Color color, float factor) {
        Color result = color.cpy();
        result.r *= factor;
        result.g *= factor;
        result.b *= factor;
        return result;
    }
}
