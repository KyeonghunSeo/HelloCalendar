package com.hellowo.hellocal;

import android.content.Context;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public abstract class CalendarModule {
    HelloCalendarView calendarView;
    Context context;
    Canvas canvas;
    Look look;

    public CalendarModule(HelloCalendarView helloCalendarView) {
        calendarView = helloCalendarView;
        context = helloCalendarView.context;
        canvas = helloCalendarView.canvas;
        look = helloCalendarView.look;
    }
}
