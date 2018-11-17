package com.z.newsleak;

import android.app.Application;
import android.util.Log;

import com.z.newsleak.data.db.AppDatabase;

import androidx.room.Room;
import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String LOG_TAG = "ErrorHandler";
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(e -> Log.d(LOG_TAG, "Error on " + Thread.currentThread().getName() + ":", e));

        database = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}