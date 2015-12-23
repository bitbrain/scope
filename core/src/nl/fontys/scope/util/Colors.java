package nl.fontys.scope.util;

import com.badlogic.gdx.graphics.Color;

public final class Colors {

    public static final Color PRIMARY = Color.valueOf("00ff5a");
    public static final Color SECONDARY = Color.valueOf("28ffb1");
    public static final Color BACKGROUND = Color.valueOf("0e0217");
    public static final Color ACTIVE = Color.valueOf("ff7800");
    public static final Color UI = Color.valueOf("00ffa2");

    public static Color trans(Color color, float alpha) {
        Color cpy = color.cpy();
        cpy.a = alpha;
        return cpy;
    }

    public static Color lighten(Color color, float factor) {
        Color result = color.cpy();
        result.r *= factor;
        result.g *= factor;
        result.b *= factor;
        return result;
    }
}
