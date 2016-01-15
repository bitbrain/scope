package nl.fontys.scope.tweens;

import com.badlogic.gdx.graphics.g3d.environment.PointLight;

import aurelienribon.tweenengine.TweenAccessor;

public class PointLightTween implements TweenAccessor<PointLight> {

    public static final int INTENSITY = 1;


    @Override
    public int getValues(PointLight target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case INTENSITY:
                returnValues[0] = target.intensity;
                return 1;
        }
        return 0;
    }

    @Override
    public void setValues(PointLight target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case INTENSITY:
                target.intensity = newValues[0];
                break;
        }
    }
}