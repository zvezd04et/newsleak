package com.z.newsleak.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.z.newsleak.R;


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
    public static int getDisplayColumns(@NonNull Activity activity) {
        int columnCount = 1;
        if (isTablet(activity)) {
            columnCount = 2;
        }
        int orientation = activity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnCount++;
        }
        return columnCount;
    }

}
