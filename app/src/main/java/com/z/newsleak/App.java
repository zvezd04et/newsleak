package com.z.newsleak;

import android.app.Application;
import android.util.Log;

import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String LOG_TAG = "ErrorHandler";

    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(e -> Log.d(LOG_TAG, "Error on " + Thread.currentThread().getName() + ":", e));
    }
}