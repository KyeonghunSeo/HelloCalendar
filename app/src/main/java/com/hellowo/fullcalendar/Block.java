package com.hellowo.fullcalendar;

import com.hellowo.hellocal.interfaces.CalendarBlockInterface;

/**
 * Created by Day2Life Android Dev on 2017-04-10.
 */

public class Block implements CalendarBlockInterface {
    long id;
    long dtStart;
    long dtEnd;
    int color;
    String title;

    public long getId() {
        return id;
    }

    @Override
    public long getDtStart() {
        return dtStart;
    }

    @Override
    public long getDtEnd() {
        return dtEnd;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", dtStart=" + dtStart +
                ", dtEnd=" + dtEnd +
                ", color=" + color +
                ", title='" + title + '\'' +
                '}';
    }
}
