package com.hellowo.hellocal.model;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import static com.hellowo.hellocal.model.HelloCalendar.MAX_COLUMNS;
import static com.hellowo.hellocal.model.HelloCalendar.MAX_ROWS;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
class Lines extends CalendarModule {
    private ImageView[] verticalLineViews;
    private ImageView[] horizontalLineViews;

    Lines(HelloCalendar helloCalendar) {
        super(helloCalendar);
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

    void draw() {
        int dayOfWeekOffset = look.isDayOfWeekVisible ? look.dayOfWeekHeight : 0;
        int width = calendarView.getWidth();
        int height = calendarView.getHeight() - dayOfWeekOffset;

        float deltaX = width / canvas.columns;
        float deltaY = height / canvas.rows;

        for(int i = 0; i < verticalLineViews.length; i++) {
            verticalLineViews[i].setTranslationX(deltaX * (i + 1));
        }

        for(int i = 0; i < horizontalLineViews.length; i++) {
            horizontalLineViews[i].setTranslationY(
                    deltaY * (i + 1) + dayOfWeekOffset);
        }
    }
}
