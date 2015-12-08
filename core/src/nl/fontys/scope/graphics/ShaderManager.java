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
        bloom = new Bloom(Math.round(Gdx.graphics.getWidth() * 0.2f), Math.round(Gdx.graphics.getHeight() * 0.2f));
        bloom.setBlurAmount(10f);
        bloom.setBloomIntesity(1.5f);
        bloom.setBlurPasses(2);
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
        INSTANCE.vignette.setEnabled(true);
        INSTANCE.zoomer.setEnabled(true);
        INSTANCE.fxaa.setEnabled(true);
        INSTANCE.bloom.setEnabled(true);
        INSTANCE.lenseflare.setEnabled(true);
        INSTANCE.zoomer.setBlurStrength(0f);
        INSTANCE.zoomer.setZoom(1f);
        INSTANCE.vignette.setIntensity(1.0f);
        INSTANCE.lenseflare.setBlurAmount(20f);
        INSTANCE.lenseflare.setBlurPasses(3);
        INSTANCE.lenseflare.setGhosts(10);
        INSTANCE.lenseflare.setFlareIntesity(0.12f);
        INSTANCE.bloom.setBaseSaturation(0.9f);
        INSTANCE.bloom.setBaseIntesity(0.7f);
    }

    public static void configureUI() {
        INSTANCE.vignette.setEnabled(false);
        INSTANCE.zoomer.setEnabled(false);
        INSTANCE.fxaa.setEnabled(false);
        INSTANCE.bloom.setEnabled(true);
        INSTANCE.lenseflare.setEnabled(false);
        INSTANCE.bloom.setBaseSaturation(1.1f);
        INSTANCE.bloom.setBaseIntesity(1.3f);
    }
}
