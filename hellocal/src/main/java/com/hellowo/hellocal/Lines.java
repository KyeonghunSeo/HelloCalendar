package com.hellowo.hellocal;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static com.hellowo.hellocal.HelloCalendarView.MAX_COLUMNS;
import static com.hellowo.hellocal.HelloCalendarView.MAX_ROWS;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
class Lines extends CalendarModule {
    private ImageView[] verticalLineViews;
    private ImageView[] horizontalLineViews;

    Lines(HelloCalendarView helloCalendarView) {
        super(helloCalendarView);
        createViews();
        setLayoutParams();
    }

    private void createViews() {
        verticalLineViews = new ImageView[MAX_COLUMNS - 1];
        horizontalLineViews = new ImageView[MAX_ROWS - 1];

        for(int i = 0; i < verticalLineViews.length; i++) {
            verticalLineViews[i] = new ImageView(context);
            calendarView.addView(verticalLineViews[i]);
        }

        for(int i = 0; i < horizontalLineViews.length; i++) {
            horizontalLineViews[i] = new ImageView(context);
            calendarView.addView(horizontalLineViews[i]);
        }
    }

    private void setLayoutParams() {
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(
                look.verticalLineWidth, ViewGroup.LayoutParams.MATCH_PARENT);

        for (ImageView verticalLineView : verticalLineViews) {
            verticalLineView.setLayoutParams(vlp);
            setLineLook(verticalLineView);
        }

        FrameLayout.LayoutParams hlp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, look.horizontalLineHeight);

        for (ImageView horizontalLineView : horizontalLineViews) {
            horizontalLineView.setLayoutParams(hlp);
            setLineLook(horizontalLineView);
        }
    }

    private void setLineLook(ImageView lineView) {
        if(look.lineStyle == Look.LineStyle.Color) {
            lineView.setBackgroundColor(look.lineColor);
        }else {

        }
    }

    void draw(boolean aniamtion) {
        if(aniamtion) {
            final AnimatorSet animSet = new AnimatorSet();
            List<Animator> animatorList = new ArrayList<>();

            for(int i = 0; i < verticalLineViews.length; i++) {
                animatorList.add(
                        ObjectAnimator.ofFloat(verticalLineViews[i], "translationX",
                                verticalLineViews[i].getTranslationX(),
                                canvas.computeVerticalLineTranslationX(i))
                                .setDuration(250)
                );

                animatorList.add(
                        ObjectAnimator.ofFloat(verticalLineViews[i], "translationY",
                                verticalLineViews[i].getTranslationY(),
                                canvas.computeVerticalLineTranslationY())
                                .setDuration(250)
                );
            }

            for(int i = 0; i < horizontalLineViews.length; i++) {
                animatorList.add(
                        ObjectAnimator.ofFloat(horizontalLineViews[i], "translationY",
                                horizontalLineViews[i].getTranslationY(),
                                canvas.computeHorizontalLineTranslationY(i))
                                .setDuration(250)
                );
            }

            animSet.playTogether(animatorList);
            animSet.setInterpolator(new FastOutSlowInInterpolator());
            animSet.start();
        }else {
            for(int i = 0; i < verticalLineViews.length; i++) {
                verticalLineViews[i].setTranslationX(
                        canvas.computeVerticalLineTranslationX(i)
                );
                verticalLineViews[i].setTranslationY(
                        canvas.computeVerticalLineTranslationY()
                );
            }

            for(int i = 0; i < horizontalLineViews.length; i++) {
                horizontalLineViews[i].setTranslationY(
                        canvas.computeHorizontalLineTranslationY(i)
                );
            }
        }
    }

}
