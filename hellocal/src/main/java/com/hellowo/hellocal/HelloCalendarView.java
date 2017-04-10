package com.hellowo.hellocal;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hellowo.hellocal.model.HelloCalendar;

import static com.hellowo.hellocal.utils.AnimationUtil.NONE_ANIMATION;

/**
 * Created by Day2Life Android Dev on 2017-04-06
 */
public class HelloCalendarView extends ScrollView {
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

    HelloCalendar helloCalendar = new HelloCalendar();
    LinearLayout panelView;
    FrameLayout canvasView;
    int width;
    int height;

    public void init() {
        panelView = new LinearLayout(getContext());
        panelView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        canvasView = new FrameLayout(getContext());
        canvasView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        panelView.addView(canvasView);
        addView(panelView);

        helloCalendar.init(this, canvasView);
        getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                        helloCalendar.draw(NONE_ANIMATION);
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
