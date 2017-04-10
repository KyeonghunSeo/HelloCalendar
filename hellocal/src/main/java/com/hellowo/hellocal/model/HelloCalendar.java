package com.hellowo.hellocal.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.hellowo.hellocal.HelloCalendarView;
import com.hellowo.hellocal.interfaces.HelloCalendarEventInterface;

import java.util.Calendar;

import static com.hellowo.hellocal.utils.AnimationUtil.SHOW_ANIMATION;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
public class HelloCalendar {
    final static String TAG = HelloCalendar.class.getName();
    final static int MAX_ROWS = 6;
    final static int MAX_COLUMNS = 7;
    enum ViewType {Monthly, Weekly, Daily}

    Context context;
    HelloCalendarView rootView;
    FrameLayout canvasView;

    Look look;
    Canvas canvas;
    Lines lines;
    Cells cells;
    DayOfWeeks dayOfWeeks;

    ViewType viewType = ViewType.Monthly;
    Calendar calendar;

    HelloCalendarEventInterface eventInterface = new HelloCalendarEventInterface() {
        @Override
        public void onClickDate(View view, int year, int month, int day) {
            Log.i(TAG, "onClickDate : " + year + "/" + (month + 1) + "/" +day);
        }

        @Override
        public void onLongClickDate(View view, int year, int month, int day) {
            Log.i(TAG, "onLongClickDate : " + year + "/" + (month + 1) + "/" +day);
        }
    };

    public void init(HelloCalendarView helloCalendarView, FrameLayout canvasView) {
        context = helloCalendarView.getContext();

        rootView = helloCalendarView;
        this.canvasView = canvasView;

        look = new Look(this);
        canvas = new Canvas(this);

        /**
         * 생성자가 불려지는 순서데로 뷰가 추가 됨
         */
        lines = new Lines(this);
        cells = new Cells(this);
        dayOfWeeks = new DayOfWeeks(this);

        calendar = Calendar.getInstance();
    }

    public void draw(boolean animation) {
        canvas.calculateCalendarBluePrint(calendar, viewType);
        canvas.draw();
        dayOfWeeks.draw(animation);
        lines.draw(animation);
        cells.draw(animation);
    }

    public void nextMonth() {
        calendar.add(Calendar.MONTH, 1);
        draw(SHOW_ANIMATION);
    }

    public void prevMonth() {
        calendar.add(Calendar.MONTH, -1);
        draw(SHOW_ANIMATION);
    }

}
