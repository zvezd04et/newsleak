package com.z.newsleak.utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.FORMAT_ABBREV_TIME;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.WEEK_IN_MILLIS;

public class DateFormatUtils {
    @Nullable
    public static CharSequence getRelativeDateTime(@NonNull Context context, @Nullable Date date) {
        if (date == null) {
            return null;
        }

        final int flags = FORMAT_ABBREV_RELATIVE |
                FORMAT_SHOW_DATE | FORMAT_ABBREV_TIME | FORMAT_ABBREV_MONTH;

        return DateUtils.getRelativeDateTimeString(context, date.getTime(),
                MINUTE_IN_MILLIS, WEEK_IN_MILLIS, flags);
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
