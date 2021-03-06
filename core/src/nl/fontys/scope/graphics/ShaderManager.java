package nl.fontys.scope.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.Fxaa;
import com.bitfire.postprocessing.effects.Vignette;
import com.bitfire.postprocessing.effects.Zoomer;
import com.bitfire.postprocessing.filters.RadialBlur;
import com.bitfire.utils.ShaderLoader;

public final class ShaderManager {

    private static final boolean isDesktop = (Gdx.app.getType() == Application.ApplicationType.Desktop);

    private static ShaderManager INSTANCE;

    private PostProcessor processor;

    public Bloom bloom;

    public Zoomer zoomer;

    public Fxaa fxaa;

    public Vignette vignette;

    private Bloom uiBloom;

    private static boolean compiled;

    private ShaderManager() {
        ShaderLoader.BasePath = "postprocessing/shaders/";
        processor = new PostProcessor( true, true, isDesktop );
        zoomer = new Zoomer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), RadialBlur.Quality.High);
        zoomer.setBlurStrength(0f);
        zoomer.setZoom(1f);
        processor.addEffect(zoomer);
        vignette = new Vignette(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        processor.addEffect(vignette);
        uiBloom = new Bloom(Math.round(Gdx.graphics.getWidth() * 0.25f), Math.round(Gdx.graphics.getHeight() * 0.25f));
        uiBloom.setBlurAmount(30f);
        uiBloom.setBloomIntesity(2.0f);
        uiBloom.setBlurPasses(4);
        processor.addEffect(uiBloom);
        uiBloom.setEnabled(false);
        bloom = new Bloom(Math.round(Gdx.graphics.getWidth()), Math.round(Gdx.graphics.getHeight()));
        bloom.setBlurAmount(10f);
        bloom.setBloomIntesity(1.1f);
        bloom.setBlurPasses(4);
        processor.addEffect(bloom);
        fxaa = new Fxaa(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        processor.addEffect(fxaa);
    }

    public static ShaderManager getBaseInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShaderManager();
            configureBase();
            compiled = true;
        }
        return INSTANCE;
    }

    public boolean isCompiled() {
        return compiled;
    }

    public void begin() {
        processor.capture();
    }

    public void end(FrameBuffer buffer) {
        processor.render(buffer);
    }

    public void dispose() {
        processor.dispose();
    }

    public void resume() {
        processor.rebind();
    }

    public static void configureBase() {
        INSTANCE.uiBloom.setEnabled(false);
        INSTANCE.vignette.setEnabled(true);
        INSTANCE.zoomer.setEnabled(true);
        INSTANCE.fxaa.setEnabled(isDesktop);
        INSTANCE.bloom.setEnabled(true);
        INSTANCE.vignette.setIntensity(0.85f);
        INSTANCE.bloom.setBaseIntesity(0.4f);
        INSTANCE.bloom.setBaseSaturation(0.4f);
    }

    public static void configureUI() {
        INSTANCE.uiBloom.setEnabled(true);
        INSTANCE.vignette.setEnabled(false);
        INSTANCE.zoomer.setEnabled(false);
        INSTANCE.fxaa.setEnabled(false);
        INSTANCE.bloom.setEnabled(false);
        INSTANCE.uiBloom.setBaseIntesity(1.2f);
        INSTANCE.uiBloom.setBaseSaturation(1.2f);
    }
}
