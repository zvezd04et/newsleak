package com.z.newsleak.utils;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;

public class DateFormatUtils {

    @NonNull
    private static DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());

    private DateFormatUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }

    @NonNull
    public static String getRelativeDateWithMinutes(@NonNull Date publishDate) {

        CharSequence relativeDate;
        relativeDate = DateUtils.getRelativeTimeSpanString(publishDate.getTime(),
                System.currentTimeMillis(),
                DateUtils.DAY_IN_MILLIS);

        String formattedTime = DateFormatUtils.timeInstance.format(publishDate);

        return relativeDate.toString() + " " + formattedTime ;
    }
}
