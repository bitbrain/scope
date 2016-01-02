package nl.fontys.scope.tweens;

import com.bitfire.postprocessing.effects.Zoomer;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Miguel on 1/2/2016.
 */
public class ZoomerShaderTween implements TweenAccessor<Zoomer> {

    public static final int BLUR_STRENGTH = 1;

    public static final int ZOOM = 2;

    @Override
    public int getValues(Zoomer target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case BLUR_STRENGTH:
                returnValues[0] = target.getBlurStrength();
                return 1;
            case ZOOM:
                returnValues[0] = target.getZoom();
                return 1;
        }
        return 0;
    }

    @Override
    public void setValues(Zoomer target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case BLUR_STRENGTH:
                target.setBlurStrength(newValues[0]);
                break;
            case ZOOM:
                target.setZoom(newValues[0]);
                break;
        }
    }
}
