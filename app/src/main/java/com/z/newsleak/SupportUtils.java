package com.z.newsleak;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.text.format.DateUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;

public class SupportUtils {

    @NonNull
    private static DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());

    private SupportUtils() {
        throw new IllegalAccessError("It's util class");
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

    @NonNull
    public static String getFormatPublishDate(@NonNull Date publishDate) {

        CharSequence relativeDate;
        relativeDate = DateUtils.getRelativeTimeSpanString(publishDate.getTime(),
                System.currentTimeMillis(),
                DateUtils.DAY_IN_MILLIS);

        String formattedTime = timeInstance.format(publishDate);

        return relativeDate.toString() + " " + formattedTime ;
    }

    @NonNull
    public static RequestManager getImageLoader(@NonNull Context context) {
        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.preview_placeholder)
                .fallback(R.drawable.preview_placeholder)
                .centerCrop();
        return  Glide.with(context).applyDefaultRequestOptions(imageOption);
    }
}
