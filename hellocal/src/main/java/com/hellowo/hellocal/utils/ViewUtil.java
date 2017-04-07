package com.hellowo.hellocal.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

/**
 * Created by Day2Life Android Dev on 2017-04-07
 */
public class ViewUtil {

    public static int dpToPx(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return px;
    }

}
