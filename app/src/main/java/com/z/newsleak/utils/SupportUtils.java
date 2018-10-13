package com.z.newsleak.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class SupportUtils {

    private static final int COLUMN_WIDTH_DP = 270;

    public static int getNewsColumnsCount(@NonNull Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        if (screenWidthDp < COLUMN_WIDTH_DP) {
            return 1;
        }
        return (int) (screenWidthDp / COLUMN_WIDTH_DP);
    }

    private SupportUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }

}
