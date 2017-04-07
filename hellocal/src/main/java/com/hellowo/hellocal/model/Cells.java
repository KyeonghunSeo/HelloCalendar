package com.hellowo.hellocal.model;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hellowo.hellocal.interfaces.HelloCalendarEventInterface;
import com.hellowo.hellocal.utils.AnimationUtil;

import static com.hellowo.hellocal.model.HelloCalendar.MAX_COLUMNS;
import static com.hellowo.hellocal.model.HelloCalendar.MAX_ROWS;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public class Cells extends CalendarModule  {
    TextView[] dateTexts;
    View[] cells;

    public Cells(HelloCalendar helloCalendar) {
        super(helloCalendar);
        createViews();
        setLayoutParams();
        setEvents(helloCalendar.eventInterface);
    }

    private void createViews() {
        int maxCellCount = MAX_COLUMNS * MAX_ROWS;

        dateTexts = new TextView[maxCellCount];

        for(int i = 0; i < maxCellCount; i++) {
            dateTexts[i] = new TextView(context);
            calendarView.addView(dateTexts[i]);
        }

        cells = new View[maxCellCount];

        for(int i = 0; i < maxCellCount; i++) {
            cells[i] = new View(context);
            calendarView.addView(cells[i]);
        }
    }

    private void setLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, look.textViewHeight);

        for (TextView dateText : dateTexts) {
            dateText.setLayoutParams(lp);
            setTextLook(dateText);
        }

        for (View cell : cells) {
            setCellLook(cell);
        }
    }

    private void setTextLook(TextView dateText) {
        dateText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, look.textSizeDp);
        dateText.setTypeface(look.textFont);
        dateText.setGravity(look.textGravity);
        dateText.setPadding(look.textPadding, look.textPadding, look.textPadding, look.textPadding);
        dateText.setTextColor(look.textColor);
    }

    private void setCellLook(View cell) {
        cell.setBackgroundResource(look.cellBackgroudResource);
    }

    private void setEvents(final HelloCalendarEventInterface eventInterface) {
        for(int i = 0; i < cells.length; i++) {
            final int finalI = i;
            cells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int[] date = canvas.getCellDate(finalI);
                    //AnimationUtil.defaultCellClickAnimation(dateTexts[finalI]);
                    eventInterface.onClickDate(view, date[0], date[1], date[2]);
                }
            });

            cells[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int[] date = canvas.getCellDate(finalI);
                    //AnimationUtil.defaultCellClickAnimation(dateTexts[finalI]);
                    eventInterface.onLongClickDate(view, date[0], date[1], date[2]);
                    return false;
                }
            });
        }
    }

    void draw() {
        int dayOfWeekOffset = look.isDayOfWeekVisible ? look.dayOfWeekHeight : 0;
        int width = calendarView.getWidth();
        int height = calendarView.getHeight() - dayOfWeekOffset;

        float deltaX = width / canvas.columns;
        float deltaY = height / canvas.rows;

        int margin = look.textMargin;

        for(int i = 0; i < cells.length; i++) {
            TextView dateText = dateTexts[i];
            dateText.setText(String.valueOf(canvas.dates[i]));
            dateText.getLayoutParams().width = (int)deltaX;
            dateText.setTranslationX(deltaX * (i % canvas.columns));
            dateText.setTranslationY(deltaY * (i / canvas.columns) + margin + dayOfWeekOffset);

            View cell = cells[i];
            cell.getLayoutParams().width = (int) deltaX;
            cell.getLayoutParams().height = (int) deltaY;
            cell.setTranslationX(deltaX * (i % canvas.columns));
            cell.setTranslationY(deltaY * (i / canvas.columns) + dayOfWeekOffset);
        }
    }

}
