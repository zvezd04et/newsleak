package com.z.newsleak.utils;

import android.os.Handler;
import android.util.Log;

import com.z.newsleak.features.newsfeed.NewsListLoader;
import com.z.newsleak.features.newsfeed.NewsListView;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

public class DataLoadUtils implements Runnable {
    @NonNull
    private final WeakReference<Handler> handlerRef;
    @NonNull private final WeakReference<NewsListView> loadingRef;

    public DataLoadUtils(@NonNull Handler handler, @NonNull NewsListView loadingView) {
        handlerRef = new WeakReference<>(handler);
        loadingRef = new WeakReference<>(loadingView);
    }

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.e("Utils", e.getMessage());
        }

        if (Thread.interrupted()) return;
        final Handler handler = handlerRef.get();
        final NewsListView loadingView = loadingRef.get();

        if (handler != null) {
            handler.post(new NewsListLoader(loadingView));
        }
    }

}