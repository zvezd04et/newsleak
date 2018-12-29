package com.z.newsleak.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewUtils {
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

    private ViewUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }
}
