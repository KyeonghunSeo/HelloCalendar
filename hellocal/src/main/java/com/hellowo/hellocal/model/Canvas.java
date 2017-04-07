package com.hellowo.hellocal.model;

import android.content.Context;

import java.util.Calendar;

import static com.hellowo.hellocal.model.HelloCalendar.MAX_COLUMNS;
import static com.hellowo.hellocal.model.HelloCalendar.MAX_ROWS;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
class Canvas {
    Context context;

    int rows;
    int columns;
    int startDayOfWeek;
    int monthlyFirstDayPos;
    int monthlyLastDayPos;
    int[] years;
    int[] months;
    int[] dates;

    Canvas(Context context) {
        this.context = context;
        years = new int[MAX_ROWS * MAX_COLUMNS];
        months = new int[MAX_ROWS * MAX_COLUMNS];
        dates = new int[MAX_ROWS * MAX_COLUMNS];
    }

    void calculateCalendarBluePrint(Calendar calendar, HelloCalendar.ViewType viewType) {
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

        Calendar tempCal = (Calendar)calendar.clone();

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
}
