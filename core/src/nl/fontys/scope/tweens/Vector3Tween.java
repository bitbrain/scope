package nl.fontys.scope.tweens;

import com.badlogic.gdx.math.Vector3;

import aurelienribon.tweenengine.TweenAccessor;

public class Vector3Tween implements TweenAccessor<Vector3> {

    public static final int POS_X = 1;

    public static final int POS_Y = 2;

    public static final int POS_Z = 3;

    @Override
    public int getValues(Vector3 target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POS_X:
                returnValues[0] = target.x;
                return 1;
            case POS_Y:
                returnValues[0] = target.y;
                return 1;
            case POS_Z:
                returnValues[0] = target.z;
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public void setValues(Vector3 target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POS_X:
                target.x = newValues[0];
                break;
            case POS_Y:
                target.y = newValues[0];
                break;
            case POS_Z:
                target.z = newValues[0];
                break;
        }
    }

}