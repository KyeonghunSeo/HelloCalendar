package com.hellowo.hellocal;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hellowo.hellocal.interfaces.HelloCalendarEventInterface;

import java.util.ArrayList;
import java.util.List;

import static com.hellowo.hellocal.HelloCalendarView.MAX_COLUMNS;
import static com.hellowo.hellocal.HelloCalendarView.MAX_ROWS;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public class Cells extends CalendarModule  {
    TextView[] dateTexts;
    View[] cells;

    public Cells(HelloCalendarView helloCalendarView) {
        super(helloCalendarView);
        createViews();
        setLayoutParams();
        setEvents(helloCalendarView.eventInterface);
    }

    private void createViews() {
        int maxCellCount = MAX_COLUMNS * MAX_ROWS;

        dateTexts = new TextView[maxCellCount];

        for(int i = 0; i < maxCellCount; i++) {
            dateTexts[i] = new TextView(context);
            calendarView.addView(dateTexts[i]);
        }

        cells = new View[maxCellCount];

        for(int i = 0; i < maxCellCount; i++) {
            cells[i] = new View(context);
            calendarView.addView(cells[i]);
        }
    }

    private void setLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, look.textViewHeight);

        for (TextView dateText : dateTexts) {
            dateText.setLayoutParams(lp);
            setTextLook(dateText);
        }

        for (View cell : cells) {
            setCellLook(cell);
        }
    }

    private void setTextLook(TextView dateText) {
        dateText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, look.textSizeDp);
        dateText.setTypeface(look.textFont);
        dateText.setGravity(look.textGravity);
        dateText.setPadding(look.textPadding, look.textPadding, look.textPadding, look.textPadding);
        dateText.setTextColor(look.textColor);
    }

    private void setCellLook(View cell) {
        cell.setBackgroundResource(look.cellBackgroudResource);
    }

    private void setEvents(final HelloCalendarEventInterface eventInterface) {
        for(int i = 0; i < cells.length; i++) {
            final int finalI = i;
            cells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int[] date = canvas.getCellDate(finalI);
                    //AnimationUtil.defaultCellClickAnimation(dateTexts[finalI]);
                    eventInterface.onClickDate(view, date[0], date[1], date[2]);
                }
            });

            cells[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int[] date = canvas.getCellDate(finalI);
                    //AnimationUtil.defaultCellClickAnimation(dateTexts[finalI]);
                    eventInterface.onLongClickDate(view, date[0], date[1], date[2]);
                    return false;
                }
            });
        }
    }

    void draw(boolean animation) {
        for(int i = 0; i < cells.length; i++) {
            canvas.drawDateText(dateTexts[i], i);

            View cell = cells[i];
            cell.setLayoutParams(new FrameLayout.LayoutParams(
                    (int) canvas.deltaX, (int) canvas.deltaY));
            cell.setTranslationX(canvas.computeCellTranslationX(i));
            cell.setTranslationY(canvas.computeCellTranslationY(i));
        }

        if(animation) {
            final AnimatorSet animSet = new AnimatorSet();
            List<Animator> animatorList = new ArrayList<>();

            for(int i = 0; i < cells.length; i++) {
                TextView dateText = dateTexts[i];

                animatorList.add(
                        ObjectAnimator.ofFloat(dateText, "translationX",
                                dateText.getTranslationX(),
                                canvas.computeDateTextTranslationX(i))
                                .setDuration(250)
                );

                animatorList.add(
                        ObjectAnimator.ofFloat(dateText, "translationY",
                                dateText.getTranslationY(),
                                canvas.computeDateTextTranslationY(i))
                                .setDuration(250)
                );
                /*
                Animator scaleX = ObjectAnimator.ofFloat(dateText, "scaleX", 0f, 1f)
                        .setDuration(250);
                scaleX.setStartDelay(i * 20);

                Animator scaleY = ObjectAnimator.ofFloat(dateText, "scaleY", 0f, 1f)
                        .setDuration(250);
                scaleY.setStartDelay(i * 20);

                animatorList.add(scaleX);
                animatorList.add(scaleY);
                */
            }

            animSet.playTogether(animatorList);
            animSet.setInterpolator(new FastOutSlowInInterpolator());
            animSet.start();
        }else {
            for(int i = 0; i < cells.length; i++) {
                TextView dateText = dateTexts[i];
                dateText.setTranslationX(canvas.computeDateTextTranslationX(i));
                dateText.setTranslationY(canvas.computeDateTextTranslationY(i));
            }
        }
    }
}
