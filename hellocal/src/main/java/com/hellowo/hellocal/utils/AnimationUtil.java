package com.hellowo.hellocal.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public class AnimationUtil {
    public final static boolean SHOW_ANIMATION = true;
    public final static boolean NONE_ANIMATION = false;
    public final static long DEFAULT_ANIMATION_DURATION = 250;

    public static void defaultCellClickAnimation(View view){
        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX",
                        0.8f, 1f).setDuration(500),
                ObjectAnimator.ofFloat(view, "scaleY",
                        0.8f, 1f).setDuration(500)
        );
        animSet.setInterpolator(new OvershootInterpolator(2f));
        animSet.start();
    }
}
