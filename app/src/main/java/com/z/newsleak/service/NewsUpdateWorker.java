package com.z.newsleak.service;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NewsUpdateWorker extends Worker {

    private static final String NEWS_UPDATE_TAG = "NEWS_UPDATE_TAG";
    private static final int NEWS_UPDATE_REPEAT_INTERVAL_IN_HOURS = 3;
    @NonNull
    private Context context;

    public static void enqueueWorker() {
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

    public NewsUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        NewsUpdateService.start(context);
        return Result.success();
    }
}
