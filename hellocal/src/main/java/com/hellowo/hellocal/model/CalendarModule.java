package com.hellowo.hellocal.model;

import android.content.Context;
import android.widget.FrameLayout;

import com.hellowo.hellocal.HelloCalendarView;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public abstract class CalendarModule {
    Context context;
    Canvas canvas;
    Look look;
    HelloCalendarView calendarView;
    FrameLayout canvasView;

    public CalendarModule(HelloCalendar helloCalendar) {
        context = helloCalendar.context;
        canvas = helloCalendar.canvas;
        look = helloCalendar.look;
        this.calendarView = helloCalendar.rootView;
        this.canvasView = helloCalendar.canvasView;
    }
}
