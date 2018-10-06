package com.z.newsleak;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

public class ScreenUtils {

    public static boolean isTablet(@NonNull Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public static int getDisplayColumns(@NonNull Activity activity) {
        int columnCount = 1;
        if (isTablet(activity)) {
            columnCount = 2;
        }
        return columnCount;
    }
}
