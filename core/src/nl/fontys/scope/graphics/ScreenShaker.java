package nl.fontys.scope.graphics;


import com.badlogic.gdx.math.Vector3;

import java.security.SecureRandom;
import java.util.UUID;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.tweens.Vector3Tween;

public class ScreenShaker {

    // Interval in miliseconds between each movement
    public static final float STEP_INTERVAL = 0.03f;

    private Vector3 shake, tmp;

    // our tween manager provided by Universal Tween Engine
    private TweenManager tweenManager;

    // We use a random to select an angle at random
    private SecureRandom random = new SecureRandom(UUID.randomUUID().toString().getBytes());

    static {
        // it is important to tell Universal Tween Engine how
        // to translate the camera movement
        Tween.registerAccessor(Vector3.class, new Vector3Tween());
    }

    // Here we're getting our dependencies
    public ScreenShaker(TweenManager tweenManager) {
        this.tweenManager = tweenManager;
        shake = new Vector3();
        tmp = new Vector3();
    }

    public Vector3 getShake() {
        return shake;
    }

    // strength is the maximum radius
    // duration is the time in miliseconds
    public void shake(float strength, final float duration, float globalDelay) {
        // Calculate the number of steps to take until radius is 0
        final int STEPS = Math.round(duration / STEP_INTERVAL);
        // Radius reduction on each iteration
        final float STRENGTH_STEP = strength / STEPS;
        // Do not forget to kill previous animations!
        tweenManager.killTarget(shake);
        for (int step = 0; step < STEPS; ++step) {
            // Step 1: Let's find a random angle
            tmp.setToRandomDirection();
            tmp.setLength(strength);

            // Step 2: ease to the calculated point. Do not forget to set
            // delay!
            Tween.to(shake, Vector3Tween.POS_X, STEP_INTERVAL).delay(globalDelay + step * STEP_INTERVAL).target(tmp.x)
                    .ease(TweenEquations.easeInOutCubic).start(tweenManager);
            Tween.to(shake, Vector3Tween.POS_Y, STEP_INTERVAL).delay(globalDelay + step * STEP_INTERVAL).target(tmp.y)
                    .ease(TweenEquations.easeInOutCubic).start(tweenManager);
            Tween.to(shake, Vector3Tween.POS_Z, STEP_INTERVAL).delay(globalDelay + step * STEP_INTERVAL).target(tmp.z)
                    .ease(TweenEquations.easeInOutCubic).start(tweenManager);

            // Step 3: reduce the radius of the screen shake circle
            strength -= STRENGTH_STEP;
        }
    }
}