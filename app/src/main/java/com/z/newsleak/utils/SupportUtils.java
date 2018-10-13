package com.z.newsleak.utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

public class SupportUtils {

    private SupportUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }

    @NonNull
    public static boolean isTablet(@NonNull Context context) {

        int screenLayoutSizeMask = context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        boolean xlarge = (screenLayoutSizeMask == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = (screenLayoutSizeMask == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @NonNull
    public static int getDisplayColumns(@NonNull Context context) {
        int columnCount = 1;
        if (isTablet(context)) {
            columnCount = 2;
        }
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnCount++;
        }
        return columnCount;
    }

}
