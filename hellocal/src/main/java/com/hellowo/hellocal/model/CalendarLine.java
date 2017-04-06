package com.hellowo.hellocal.model;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hellowo.hellocal.R;
import com.hellowo.hellocal.view.HelloCalendarView;

import static com.hellowo.hellocal.model.HelloCalendar.MAX_COLUMNS;
import static com.hellowo.hellocal.model.HelloCalendar.MAX_ROWS;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
public class CalendarLine {
    ImageView[] verticalLineViews;
    ImageView[] horizontalLineViews;

    Context context;
    CalendarLook look;

    public CalendarLine(HelloCalendar helloCalendar) {
        context = helloCalendar.context;
        look = helloCalendar.look;
        createViews(helloCalendar.canvasView);
        setLayoutParams();
    }

    private void createViews(HelloCalendarView canvasView) {
        verticalLineViews = new ImageView[MAX_COLUMNS - 1];
        horizontalLineViews = new ImageView[MAX_ROWS - 1];

        for(int i = 0; i < verticalLineViews.length; i++) {
            verticalLineViews[i] = new ImageView(context);
            canvasView.addView(verticalLineViews[i]);
        }

        for(int i = 0; i < horizontalLineViews.length; i++) {
            horizontalLineViews[i] = new ImageView(context);
            canvasView.addView(horizontalLineViews[i]);
        }
    }

    private void setLayoutParams() {
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(
                look.verticalLineWidth, ViewGroup.LayoutParams.MATCH_PARENT);

        for(int i = 0; i < verticalLineViews.length; i++) {
            verticalLineViews[i].setLayoutParams(vlp);
            setLineBackground(verticalLineViews[i]);
        }

        FrameLayout.LayoutParams hlp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, look.horizontalLineWidth);

        for(int i = 0; i < horizontalLineViews.length; i++) {
            horizontalLineViews[i].setLayoutParams(hlp);
            setLineBackground(horizontalLineViews[i]);
        }
    }

    private void setLineBackground(ImageView lineView) {
        if(look.lineStyle == CalendarLook.LineStyle.Color) {
            lineView.setBackgroundColor(look.lineColor);
        }else {

        }
    }

    public void draw(HelloCalendarView canvasView) {
        int width = canvasView.getWidth();
        int height = canvasView.getHeight();

        float deltaX = width / MAX_COLUMNS;
        float deltaY = height / MAX_ROWS;

        for(int i = 0; i < verticalLineViews.length; i++) {
            verticalLineViews[i].setTranslationX(deltaX * (i + 1));
        }

        for(int i = 0; i < horizontalLineViews.length; i++) {
            horizontalLineViews[i].setTranslationY(deltaY * (i + 1));
        }
    }
}
