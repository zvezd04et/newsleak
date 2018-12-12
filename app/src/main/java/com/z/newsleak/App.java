package com.z.newsleak;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.data.db.AppDatabase;
import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.service.NewsUpdateWorker;
import com.z.newsleak.utils.NetworkUtils;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String LOG_TAG = "RxErrorHandler";
    private static final String NEWS_UPDATE_TAG = "NEWS_UPDATE_TAG";

    @NonNull
    private static AppDatabase database;
    @NonNull
    private static NewsRepository repository;
    @NonNull
    private static PreferencesManager preferencesManager;
    @NonNull
    private static Context context;

    @NonNull
    public static Context getContext() {
        return context;
    }

    @NonNull
    public static AppDatabase getDatabase() {
        return database;
    }

    @NonNull
    public static NewsRepository getRepository() {
        return repository;
    }

    @NonNull
    public static PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        database = AppDatabase.getInstance(this);
        repository = NewsRepository.getInstance();
        preferencesManager = PreferencesManager.getInstance(this);

        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e.getCause();
            }
            if ((e instanceof SocketException) || (e instanceof IOException)) {
                Log.d(LOG_TAG, "Irrelevant network problem or API that throws on cancellation", e);
                return;
            }
            if (e instanceof InterruptedException) {
                Log.d(LOG_TAG, "Some blocking code was interrupted by a dispose call", e);
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                Log.d(LOG_TAG, "That's likely a bug in the application", e);
                return;
            }
            if (e instanceof IllegalStateException) {
                Log.d(LOG_TAG, "That's a bug in RxJava or in a custom operator", e);
                return;
            }
            Log.d(LOG_TAG, "Undeliverable exception received, not sure what to do", e);
        });

        final Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        final PeriodicWorkRequest newsUpdateWork = new PeriodicWorkRequest.Builder(NewsUpdateWorker.class, 3, TimeUnit.HOURS)
                .addTag(NEWS_UPDATE_TAG)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance().enqueue(newsUpdateWork);

        registerReceiver(NetworkUtils.getInstance().getReceiver(),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

}