package com.hellowo.hellocal.model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellowo.hellocal.view.HelloCalendarView;

import java.util.Calendar;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
public class HelloCalendar {
    final static int MAX_ROWS = 6;
    final static int MAX_COLUMNS = 7;

    enum ViewType {Monthly, Weekly, Daily}
    ViewType viewType = ViewType.Monthly;

    Context context;

    HelloCalendarView canvasView;
    CalendarLook look;

    CalendarLine line;

    Calendar currentCal;

    public void init(HelloCalendarView helloCalendarView) {
        context = helloCalendarView.getContext();

        canvasView = helloCalendarView;
        look = new CalendarLook();

        line = new CalendarLine(this);

        currentCal = Calendar.getInstance();
    }

    public void draw() {
        line.draw(canvasView);
    }

}
