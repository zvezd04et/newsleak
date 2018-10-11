package com.z.newsleak.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.z.newsleak.R;

import androidx.annotation.NonNull;

public class ImageLoadUtils {

    private ImageLoadUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
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
