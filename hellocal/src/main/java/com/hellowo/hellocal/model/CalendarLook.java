package com.hellowo.hellocal.model;

import android.graphics.Color;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
public class CalendarLook {
    enum LineStyle{Color, Image}
    LineStyle lineStyle = LineStyle.Color;
    int verticalLineWidth = 1;
    int horizontalLineWidth = 1;
    int lineColor = Color.LTGRAY;
}
