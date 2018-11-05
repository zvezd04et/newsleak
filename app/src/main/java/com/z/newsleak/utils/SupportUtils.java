package com.z.newsleak.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;

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

    public static void setVisible(@Nullable View view, boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public static void setText(@Nullable TextView textView, @NonNull CharSequence text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    public static void disposeSafely(@Nullable Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    private SupportUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }

}
