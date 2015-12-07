package nl.fontys.scope.i18n;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public final class Bundle {

    public static I18NBundle general;

    private static FileHandle generalHandle;

    public static void load() {
        Gdx.app.log("LOAD", "Loading bundles...");
        generalHandle = Gdx.files.internal("i18n/general");
        Locale locale = Locale.getDefault();
        setLocale(locale);
        Gdx.app.log("INFO", "Done loading bundles.");
    }

    public static void setLocale(Locale locale) {
        Gdx.app.log("INFO", "Set locale to '" + locale + "'");
        general = I18NBundle.createBundle(generalHandle, locale);
    }
}