package com.hellowo.hellocal.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;

import com.hellowo.hellocal.R;
import com.hellowo.hellocal.utils.ViewUtil;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
class Look {
    Context context;

    float sideMonthDisplayAlpha;
    int calendarPadding;
    int calendarBackgroundResource;
    boolean isWeekendColorOn;
    int weekendColor;

    boolean isDayOfWeekVisible;
    int dayOfWeekHeight;
    int dayOfWeekTextSizeDp;
    int dayOfWeekTextColor;
    Typeface dayOfWeekTextFont;
    int dayOfWeekElevation;
    int dayOfWeekGravity;
    int dayOfWeekDividerHeight;
    int dayOfWeekDividerColor;

    enum LineStyle{Color, Image}
    LineStyle lineStyle;
    int verticalLineWidth;
    int horizontalLineHeight;
    int lineColor;

    Typeface textFont;
    int textViewHeight;
    int textSizeDp;
    int textMargin;
    int textPadding;
    int textGravity;
    int textColor;
    int textBackgroundResourcId;

    int cellBackgroudResource;

    Look(HelloCalendar helloCalendar) {
        this.context = helloCalendar.context;
        init();
    }

    private void init() {
        /**
         * 캘린더 스타일
         */
        sideMonthDisplayAlpha = 0.2f;
        calendarPadding = ViewUtil.dpToPx(0, context);
        calendarBackgroundResource = R.color.white;
        isWeekendColorOn = true;
        weekendColor = Color.parseColor("#f52f3c");

        /**
         * 요일 스타일
         */
        isDayOfWeekVisible = true;
        dayOfWeekHeight = ViewUtil.dpToPx(25, context);
        dayOfWeekTextSizeDp = 14;
        dayOfWeekTextColor = Color.GRAY;
        dayOfWeekTextFont = Typeface.DEFAULT_BOLD;
        dayOfWeekElevation = ViewUtil.dpToPx(1, context);
        dayOfWeekGravity = Gravity.CENTER;
        dayOfWeekDividerHeight = 1;
        dayOfWeekDividerColor = Color.LTGRAY;

        /**
         * 라인 스타일
         */
        lineStyle = Look.LineStyle.Color;
        verticalLineWidth = 1;
        horizontalLineHeight = 1;
        lineColor = Color.LTGRAY;

        /**
         * 날짜 텍스트 스타일
         */
        textFont = Typeface.DEFAULT_BOLD;
        textViewHeight = ViewUtil.dpToPx(25, context);
        textSizeDp = 14;
        textMargin = ViewUtil.dpToPx(1, context);
        textPadding = ViewUtil.dpToPx(3, context);
        textGravity = Gravity.CENTER;
        textColor = Color.GRAY;
        textBackgroundResourcId = -1;

        /**
         * 셀 스타일
         */
        cellBackgroudResource = R.drawable.ripple_background;
    }
}
