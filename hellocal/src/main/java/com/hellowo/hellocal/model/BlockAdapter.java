package com.hellowo.hellocal.model;

import com.hellowo.hellocal.interfaces.CalendarBlockInterface;

import java.util.List;

/**
 * Created by Day2Life Android Dev on 2017-04-10
 */
public class BlockAdapter {
    List<CalendarBlockInterface> blockList;

    public void BlockAdapter(List<CalendarBlockInterface> blockList) {
        this.blockList = blockList;
    }
}
