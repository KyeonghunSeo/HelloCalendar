package com.hellowo.hellocal.model;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hellowo.hellocal.HelloCalendarView;

import java.util.Calendar;

import static com.hellowo.hellocal.model.HelloCalendar.MAX_COLUMNS;
import static com.hellowo.hellocal.model.HelloCalendar.MAX_ROWS;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
class Canvas {
    HelloCalendarView calendarView;
    FrameLayout canvasView;
    Look look;

    int rows;
    int columns;
    int startDayOfWeek;
    int monthlyFirstDayPos;
    int monthlyLastDayPos;
    int dayOfWeekOffset;
    int weekendPos;
    int width;
    int height;
    float deltaX;
    float deltaY;
    int currentYear;
    int currentMonth;
    int currentDate;
    int[] years;
    int[] months;
    int[] dates;

    Canvas(HelloCalendar helloCalendar) {
        this.calendarView = helloCalendar.rootView;
        this.canvasView = helloCalendar.canvasView;
        this.look = helloCalendar.look;
        years = new int[MAX_ROWS * MAX_COLUMNS];
        months = new int[MAX_ROWS * MAX_COLUMNS];
        dates = new int[MAX_ROWS * MAX_COLUMNS];
    }

    void calculateCalendarBluePrint(Calendar calendar, HelloCalendar.ViewType viewType) {
        dayOfWeekOffset = look.isDayOfWeekVisible ? look.dayOfWeekHeight : 0;
        width = calendarView.getWidth() - (look.calendarPadding * 2);
        height = calendarView.getHeight() - (look.calendarPadding * 2) - dayOfWeekOffset;

        switch (viewType) {
            case Monthly:
                calculateMonthlyBluePrint(calendar);
                break;
            default:
                break;
        }
    }

    private void calculateMonthlyBluePrint(Calendar calendar) {
        columns = MAX_COLUMNS;
        rows = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        startDayOfWeek = Calendar.SUNDAY;
        weekendPos = Calendar.SUNDAY - 1;
        deltaX = width / columns;
        deltaY = height / rows;

        Calendar tempCal = (Calendar)calendar.clone();
        currentYear = tempCal.get(Calendar.YEAR);
        currentMonth = tempCal.get(Calendar.MONTH);
        currentDate = tempCal.get(Calendar.DATE);

        tempCal.set(Calendar.DATE, tempCal.getActualMaximum(Calendar.DATE));
        monthlyLastDayPos = tempCal.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;

        tempCal.set(Calendar.DATE, 1);
        monthlyFirstDayPos = tempCal.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;

        tempCal.add(Calendar.DATE, startDayOfWeek - tempCal.get(Calendar.DAY_OF_WEEK));

        for(int i = 0; i < rows * columns; i++) {
            years[i] = tempCal.get(Calendar.YEAR);
            months[i] = tempCal.get(Calendar.MONTH);
            dates[i] = tempCal.get(Calendar.DATE);
            tempCal.add(Calendar.DATE, 1);
        }
    }

    void draw() {
        canvasView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, calendarView.getHeight())
        );
        canvasView.setPadding(look.calendarPadding, look.calendarPadding,
                look.calendarPadding, look.calendarPadding);
    }

    /**
     * 해당 셀 포지션에 대한 년, 월, 일을 가져옴
     */
    int[] getCellDate(int cellPosition) {
        int[] result = new int[3];
        try{
            result[0] = years[cellPosition];
            result[1] = months[cellPosition];
            result[2] = dates[cellPosition];
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return result;
    }

    float computeDayOfWeekTextTranslationY() {
        return look.isDayOfWeekVisible ? 0 : -look.dayOfWeekHeight;
    }

    float computeDeividerTranslationY() {
        return look.isDayOfWeekVisible ? look.dayOfWeekHeight : -look.dayOfWeekHeight;
    }

    float computeVerticalLineTranslationX(int i) {
        return deltaX * (i + 1);
    }

    float computeHorizontalLineTranslationY(int i) {
        return deltaY * (i + 1) + dayOfWeekOffset;
    }

    int computeCellWidth() {
        return (int) deltaX;
    }

    float computeCellTranslationX(int i) {
        return deltaX * (i % columns);
    }

    float computeCellTranslationY(int i) {
        return deltaY * (i / columns) + dayOfWeekOffset;
    }

    float computeDateTextTranslationX(int i) {
        return deltaX * (i % columns);
    }

    float computeDateTextTranslationY(int i) {
        return deltaY * (i / columns) + look.textMargin + dayOfWeekOffset;
    }

    void drawDayOfWeekText(TextView dayOfWeekText, int i, String[] dayOfWeekStrings) {
        dayOfWeekText.setLayoutParams(new FrameLayout.LayoutParams(
                computeCellWidth(), look.dayOfWeekHeight
        ));
        dayOfWeekText.setText(dayOfWeekStrings[((startDayOfWeek - 1) + i) % MAX_COLUMNS]);
        dayOfWeekText.setTranslationX(deltaX * i);

        if(i % columns == weekendPos) {
            dayOfWeekText.setTextColor(look.weekendColor);
        }else {
            dayOfWeekText.setTextColor(look.textColor);
        }
    }

    void drawDateText(TextView dateText, int i) {
        dateText.setText(String.valueOf(dates[i]));
        dateText.setLayoutParams(new FrameLayout.LayoutParams((int) deltaX, look.textViewHeight));

        if(months[i] != currentMonth) {
            dateText.setAlpha(look.sideMonthDisplayAlpha);
        }else {
            dateText.setAlpha(1f);
        }

        if(i % columns == weekendPos) {
            dateText.setTextColor(look.weekendColor);
        }else {
            dateText.setTextColor(look.textColor);
        }
    }
}
