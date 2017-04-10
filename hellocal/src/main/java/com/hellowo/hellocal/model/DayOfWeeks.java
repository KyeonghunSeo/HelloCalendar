package com.hellowo.hellocal.model;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.hellowo.hellocal.model.HelloCalendar.MAX_COLUMNS;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public class DayOfWeeks extends CalendarModule{
    String[] dayOfWeekStrings = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    TextView[] dayOfWeekTexts;
    ImageView divider;

    DayOfWeeks(HelloCalendar helloCalendar) {
        super(helloCalendar);
        createViews();
        setLayoutParams();
    }

    private void createViews() {
        dayOfWeekTexts = new TextView[MAX_COLUMNS];
        divider = new ImageView(context);
        canvasView.addView(divider);

        for(int i = 0; i < MAX_COLUMNS; i++) {
            dayOfWeekTexts[i] = new TextView(context);
            canvasView.addView(dayOfWeekTexts[i]);
        }
    }

    private void setLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, look.dayOfWeekHeight);

        for (TextView dayOfWeekText : dayOfWeekTexts) {
            dayOfWeekText.setLayoutParams(lp);
            setTextLook(dayOfWeekText);
        }

        divider.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, look.dayOfWeekDividerHeight));
        setDividerLook(divider);
    }

    private void setTextLook(TextView dayOfWeekText) {
        dayOfWeekText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, look.dayOfWeekTextSizeDp);
        dayOfWeekText.setTypeface(look.dayOfWeekTextFont);
        dayOfWeekText.setGravity(look.dayOfWeekGravity);
        dayOfWeekText.setTextColor(look.dayOfWeekTextColor);
    }

    private void setDividerLook(ImageView divider) {
        divider.setBackgroundColor(look.dayOfWeekDividerColor);
    }

    void draw(boolean animation) {
        for(int i = 0; i < MAX_COLUMNS; i++) {
            canvas.drawDayOfWeekText(dayOfWeekTexts[i], i, dayOfWeekStrings);
        }

        if(animation) {
            final AnimatorSet animSet = new AnimatorSet();
            List<Animator> animatorList = new ArrayList<>();

            for(int i = 0; i < MAX_COLUMNS; i++) {
                animatorList.add(
                        ObjectAnimator.ofFloat(dayOfWeekTexts[i], "translationY",
                                dayOfWeekTexts[i].getTranslationY(),
                                canvas.computeDayOfWeekTextTranslationY())
                                .setDuration(250)
                );
            }
            animatorList.add(
                    ObjectAnimator.ofFloat(divider, "translationY",
                            divider.getTranslationY(),
                            canvas.computeDeividerTranslationY())
                            .setDuration(250)
            );

            animSet.playTogether(animatorList);
            animSet.setInterpolator(new FastOutSlowInInterpolator());
            animSet.start();

        }else {
            for(int i = 0; i < MAX_COLUMNS; i++) {
                dayOfWeekTexts[i].setTranslationY(canvas.computeDayOfWeekTextTranslationY());
            }
            divider.setTranslationY(look.dayOfWeekHeight);
        }
    }
}
