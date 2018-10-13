package com.z.newsleak.utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.util.Date;

import androidx.annotation.NonNull;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.HOUR_IN_MILLIS;

public class DateFormatUtils {

    private DateFormatUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }

    @NonNull
    public static CharSequence getRelativeDateTime(@NonNull Context context, @NonNull Date date) {

        return DateUtils.getRelativeDateTimeString(
                context,
                date.getTime(),
                HOUR_IN_MILLIS,
                5 * DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE
        );
    }
}
