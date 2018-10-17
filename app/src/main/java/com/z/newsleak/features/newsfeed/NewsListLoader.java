package com.z.newsleak.features.newsfeed;


import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;


public class NewsListLoader implements Runnable {
    @NonNull
    private final WeakReference<NewsListView> loadingRef;

    public NewsListLoader(@NonNull NewsListView loadingView) {
        loadingRef = new WeakReference<>(loadingView);
    }

    @Override
    public void run() {
        final NewsListView loadingView = loadingRef.get();
        if (loadingView != null) {

            loadingView.showProgress(false);
            loadingView.updateNews();
        }
    }
}
