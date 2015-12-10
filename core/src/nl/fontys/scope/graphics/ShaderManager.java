package nl.fontys.scope.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.Fxaa;
import com.bitfire.postprocessing.effects.LensFlare2;
import com.bitfire.postprocessing.effects.Vignette;
import com.bitfire.postprocessing.effects.Zoomer;
import com.bitfire.postprocessing.filters.Blur;
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

    public LensFlare2 lenseflare;

    private Bloom uiBloom;

    private ShaderManager() {
        ShaderLoader.BasePath = "postprocessing/shaders/";
        processor = new PostProcessor( true, true, isDesktop );
        zoomer = new Zoomer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), RadialBlur.Quality.High);
        processor.addEffect(zoomer);
        vignette = new Vignette(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        processor.addEffect(vignette);
        lenseflare = new LensFlare2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lenseflare.setLensColorTexture(new Texture(Gdx.files.internal("postprocessing/lenscolor.png")));
        processor.addEffect(lenseflare);
        lenseflare.setEnabled(false);
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
        }
        return INSTANCE;
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
        INSTANCE.fxaa.setEnabled(true);
        INSTANCE.bloom.setEnabled(true);
        INSTANCE.lenseflare.setEnabled(false);
        INSTANCE.zoomer.setBlurStrength(0f);
        INSTANCE.zoomer.setZoom(1f);
        INSTANCE.vignette.setIntensity(1.0f);
        INSTANCE.bloom.setBaseIntesity(0.5f);
        INSTANCE.bloom.setBaseSaturation(0.5f);
    }

    public static void configureUI() {
        INSTANCE.uiBloom.setEnabled(true);
        INSTANCE.vignette.setEnabled(false);
        INSTANCE.zoomer.setEnabled(false);
        INSTANCE.fxaa.setEnabled(false);
        INSTANCE.bloom.setEnabled(false);
        INSTANCE.lenseflare.setEnabled(false);
        INSTANCE.uiBloom.setBaseIntesity(1.1f);
        INSTANCE.uiBloom.setBaseSaturation(1.1f);
    }
}
