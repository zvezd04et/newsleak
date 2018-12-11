package com.z.newsleak.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NewsUpdateWorker extends Worker {

    @NonNull
    private Context context;

    public NewsUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        NewsUpdateService.start(context);
        return Result.SUCCESS;
    }
}
