package nl.fontys.scope.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.Fxaa;
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
        bloom = new Bloom( (int)(Gdx.graphics.getWidth() * 0.1f), (int)(Gdx.graphics.getHeight() * 0.1f) );
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
