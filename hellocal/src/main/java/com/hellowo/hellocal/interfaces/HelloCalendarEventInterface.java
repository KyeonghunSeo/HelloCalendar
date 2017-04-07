package com.hellowo.hellocal.interfaces;

import android.view.View;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public interface HelloCalendarEventInterface {
    void onClickDate(View view, int year, int month, int day);
    void onLongClickDate(View view, int year, int month, int day);
}
