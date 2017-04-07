package com.hellowo.hellocal.model;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.hellowo.hellocal.HelloCalendarView;
import com.hellowo.hellocal.interfaces.HelloCalendarEventInterface;

import java.util.Calendar;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
public class HelloCalendar {
    final static String TAG = HelloCalendar.class.getName();
    final static int MAX_ROWS = 6;
    final static int MAX_COLUMNS = 7;

    Context context;

    HelloCalendarView calendarView;

    enum ViewType {Monthly, Weekly, Daily}
    ViewType viewType = ViewType.Monthly;

    Canvas canvas;
    Look look;

    Lines lines;
    Cells cells;
    DayOfWeeks dayOfWeeks;

    Calendar calendar;

    HelloCalendarEventInterface eventInterface = new HelloCalendarEventInterface() {
        @Override
        public void onClickDate(View view, int year, int month, int day) {
            Log.i(TAG, "onClickDate : " + year + "/" + month + "/" +day);
        }

        @Override
        public void onLongClickDate(View view, int year, int month, int day) {
            Log.i(TAG, "onLongClickDate : " + year + "/" + month + "/" +day);
        }
    };

    public void init(HelloCalendarView helloCalendarView) {
        context = helloCalendarView.getContext();

        calendarView = helloCalendarView;

        canvas = new Canvas(context);
        look = new Look(context);

        /**
         * 생성자가 불려지는 순서데로 뷰가 추가 됨
         */
        lines = new Lines(this);
        cells = new Cells(this);
        dayOfWeeks = new DayOfWeeks(this);

        calendar = Calendar.getInstance();
    }

    public void draw() {
        canvas.calculateCalendarBluePrint(calendar, viewType);

        calendarView.setPadding(look.calendarPadding, look.calendarPadding,
                look.calendarPadding, look.calendarPadding);

        if(look.isDayOfWeekVisible){
            dayOfWeeks.draw();
        }else{
            dayOfWeeks.hide();
        }
        lines.draw();
        cells.draw();
    }

    public void nextMonth() {
        calendar.add(Calendar.MONTH, 1);
        draw();
    }

    public void prevMonth() {
        calendar.add(Calendar.MONTH, -1);
        draw();
    }

}
