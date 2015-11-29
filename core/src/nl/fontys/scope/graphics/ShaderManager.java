package nl.fontys.scope.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    private Bloom bloom;

    private Zoomer zoomer;

    private Fxaa fxaa;

    private Vignette vignette;

    private ShaderManager() {
        ShaderLoader.BasePath = "postprocessing/shaders/";
        processor = new PostProcessor( true, true, isDesktop );
        zoomer = new Zoomer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), RadialBlur.Quality.High);
        zoomer.setBlurStrength(0f);
        zoomer.setZoom(1f);
        zoomer.setEnabled(true);
        processor.addEffect(zoomer);
        vignette = new Vignette(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        vignette.setIntensity(1.0f);
        processor.addEffect(vignette);
        LensFlare2 lf = new LensFlare2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lf.setLensColorTexture(new Texture(Gdx.files.internal("postprocessing/lenscolor.png")));
        lf.setBlurAmount(20f);
        lf.setBlurPasses(3);
        lf.setGhosts(10);
        lf.setFlareIntesity(0.12f);
        processor.addEffect(lf);
        bloom = new Bloom(Math.round(Gdx.graphics.getWidth() * 0.25f), Math.round(Gdx.graphics.getHeight() * 0.25f));
        bloom.setBlurAmount(25f);
        bloom.setBloomIntesity(2f);
        bloom.setBlurPasses(5);
        processor.addEffect(bloom);
        fxaa = new Fxaa(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        processor.addEffect(fxaa);
    }

    public void setZoom(float zoom) {
        this.zoomer.setZoom(zoom);
    }

    public void setBlurStrength(float strength) {
        this.zoomer.setBlurStrength(strength);
    }

    public static ShaderManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShaderManager();
        }
        return INSTANCE;
    }

    public void begin() {
        processor.capture();
    }

    public void end() {
        processor.render();
    }

    public void dispose() {
        processor.dispose();
    }

    public void resume() {
        processor.rebind();
    }
}
