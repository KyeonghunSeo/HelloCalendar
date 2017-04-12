package com.hellowo.hellocal;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.hellowo.hellocal.interfaces.HelloCalendarEventInterface;

import java.util.Calendar;

import static com.hellowo.hellocal.utils.AnimationUtil.NONE_ANIMATION;
import static com.hellowo.hellocal.utils.AnimationUtil.SHOW_ANIMATION;

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

    final static String TAG = HelloCalendarView.class.getName();
    final static int MAX_ROWS = 6;
    final static int MAX_COLUMNS = 7;

    enum ViewType {Monthly, Weekly, Daily}

    ViewType viewType = ViewType.Monthly;
    Context context;
    Calendar calendar;
    Look look;
    Canvas canvas;
    Lines lines;
    Cells cells;
    DayOfWeeks dayOfWeeks;

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

    BlockAdapter blockAdapter;
    int width;
    int height;

    public void init() {
        context = getContext();
        look = new Look(this);
        canvas = new Canvas(this);
        /**
         * 생성자가 불려지는 순서데로 뷰가 추가 됨
         */
        lines = new Lines(this);
        cells = new Cells(this);
        dayOfWeeks = new DayOfWeeks(this);

        calendar = Calendar.getInstance();
        setGlobalLayoutLisnter();
    }

    private void setGlobalLayoutLisnter() {
        getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                        draw(NONE_ANIMATION);
                    }
                });
    }

    public void draw(boolean animation) {
        canvas.calculateCalendarBluePrint(calendar, viewType);
        canvas.draw();
        dayOfWeeks.draw(animation);
        lines.draw(animation);
        cells.draw(animation);
        if(blockAdapter != null) {
            canvas.drawBlock(blockAdapter);
        }
    }

    public void nextMonth() {
        calendar.add(Calendar.MONTH, 1);
        draw(SHOW_ANIMATION);
    }

    public void prevMonth() {
        calendar.add(Calendar.MONTH, -1);
        draw(SHOW_ANIMATION);
    }

    public void setBlockAdapter(BlockAdapter blockAdapter) {
        this.blockAdapter = blockAdapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
