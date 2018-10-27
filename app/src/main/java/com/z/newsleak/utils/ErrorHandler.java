package com.z.newsleak.utils;

import android.util.Log;

import io.reactivex.functions.Consumer;

public class ErrorHandler implements Consumer<Throwable> {

    private static final String LOG_TAG = "ErrorHandler";
    private static final ErrorHandler INSTANCE = new ErrorHandler();

    public static ErrorHandler get() {
        return INSTANCE;
    }

    private ErrorHandler() {

    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        Log.e(LOG_TAG, "Error on " + Thread.currentThread().getName() + ":", throwable);
    }
}
