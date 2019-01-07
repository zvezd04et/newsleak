package com.z.newsleak;

import android.app.Application;
import android.util.Log;

import com.z.newsleak.di.components.AppComponent;
import com.z.newsleak.di.components.DaggerAppComponent;
import com.z.newsleak.di.modules.AppModule;
import com.z.newsleak.service.NewsUpdateWorker;
import com.z.newsleak.utils.NetworkUtils;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String LOG_TAG = "RxErrorHandler";
    private static final String NEWS_UPDATE_TAG = "NEWS_UPDATE_TAG";
    private static final int NEWS_UPDATE_REPEAT_INTERVAL_IN_HOURS = 3;

    @Inject
    @NonNull
    NetworkUtils networkUtils;
    @NonNull
    private static AppComponent appComponent;

    @NonNull
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        networkUtils.registerNetworkCallback();

        setRxErrorHandler();
        setupWorkManager();
    }

    private void setRxErrorHandler() {
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
    }

    private void setupWorkManager() {
        final Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        final PeriodicWorkRequest newsUpdateWork = new PeriodicWorkRequest.Builder(NewsUpdateWorker.class,
                                                            NEWS_UPDATE_REPEAT_INTERVAL_IN_HOURS,
                                                            TimeUnit.HOURS)
                .addTag(NEWS_UPDATE_TAG)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(NEWS_UPDATE_TAG,
                ExistingPeriodicWorkPolicy.REPLACE, newsUpdateWork);
    }
}