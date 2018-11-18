package com.z.newsleak.utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.HOUR_IN_MILLIS;

public class DateFormatUtils {

    @Nullable
    public static CharSequence getRelativeDateTime(@NonNull Context context, @Nullable Date date) {

        if (date == null) {
            return null;
        }

        return DateUtils.getRelativeDateTimeString(
                context,
                date.getTime(),
                HOUR_IN_MILLIS,
                5 * DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE
        );
    }

    @Nullable
    public static CharSequence getRelativeDate(@Nullable Date date) {

        if (date == null) {
            return null;
        }

        return DateUtils.getRelativeTimeSpanString(date.getTime(),
                System.currentTimeMillis(),
                DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE);
    }

    @Nullable
    public static String getRelativeTime(@Nullable Date date) {

        if (date == null) {
            return null;
        }

        DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        return timeInstance.format(date);
    }


    private DateFormatUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }
}
