package com.hellowo.hellocal.model;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import static com.hellowo.hellocal.model.HelloCalendar.MAX_COLUMNS;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public class DayOfWeeks extends CalendarModule{
    String[] dayOfWeekStrings = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    TextView[] dayOfWeekTexts;
    ImageView divider;

    DayOfWeeks(HelloCalendar helloCalendar) {
        super(helloCalendar);
        createViews();
        setLayoutParams();
    }

    private void createViews() {
        dayOfWeekTexts = new TextView[MAX_COLUMNS];
        divider = new ImageView(context);
        calendarView.addView(divider);

        for(int i = 0; i < MAX_COLUMNS; i++) {
            dayOfWeekTexts[i] = new TextView(context);
            calendarView.addView(dayOfWeekTexts[i]);
        }
    }

    private void setLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, look.dayOfWeekHeight);

        for (TextView dayOfWeekText : dayOfWeekTexts) {
            dayOfWeekText.setLayoutParams(lp);
            setTextLook(dayOfWeekText);
        }

        divider.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, look.dayOfWeekDividerHeight));
        setDividerLook(divider);
    }

    private void setTextLook(TextView dayOfWeekText) {
        dayOfWeekText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, look.dayOfWeekTextSizeDp);
        dayOfWeekText.setTypeface(look.dayOfWeekTextFont);
        dayOfWeekText.setGravity(look.dayOfWeekGravity);
        dayOfWeekText.setTextColor(look.dayOfWeekTextColor);
    }

    private void setDividerLook(ImageView divider) {
        divider.setBackgroundColor(look.dayOfWeekDividerColor);
    }

    void draw() {
        int width = calendarView.getWidth();

        float deltaX = width / canvas.columns;

        for(int i = 0; i < MAX_COLUMNS; i++) {
            TextView dayOfWeekText = dayOfWeekTexts[i];
            dayOfWeekText.getLayoutParams().width = (int) deltaX;
            dayOfWeekText.setText(dayOfWeekStrings[((canvas.startDayOfWeek - 1) + i) % MAX_COLUMNS]);
            dayOfWeekText.setTranslationX(deltaX * i);
        }

        divider.setTranslationY(look.dayOfWeekHeight);
    }

    void hide() {
        for(int i = 0; i < MAX_COLUMNS; i++) {
            dayOfWeekTexts[i].setVisibility(View.GONE);
        }
        
        divider.setVisibility(View.GONE);
    }
}
