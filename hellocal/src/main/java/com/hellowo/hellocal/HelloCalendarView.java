package com.hellowo.hellocal;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.hellowo.hellocal.model.HelloCalendar;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
public class HelloCalendarView extends FrameLayout {
    public HelloCalendarView(Context context) {
        super(context);
        init();
    }

    public HelloCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HelloCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HelloCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    HelloCalendar helloCalendar = new HelloCalendar();
    int width;
    int height;

    public void init() {
        helloCalendar.init(this);
        getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                        helloCalendar.draw();
                    }
                });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void nextMonth() {
        helloCalendar.nextMonth();
    }

    public void prevMonth() {
        helloCalendar.prevMonth();
    }
}
