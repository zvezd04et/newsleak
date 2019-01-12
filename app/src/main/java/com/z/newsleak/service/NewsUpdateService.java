package com.z.newsleak.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.z.newsleak.R;
import com.z.newsleak.data.NewsInteractor;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.di.DI;
import com.z.newsleak.model.Category;
import com.z.newsleak.utils.NotificationHelper;
import com.z.newsleak.utils.RxUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsUpdateService extends Service {

    private static final String LOG_TAG = "NewsUpdateService";
    private static final String ACTION_STOP_NEWS_UPDATE = "com.z.newsleak.STOP_SELF";
    private static final int NOTIFICATION_NEWS_UPDATE_ID = 25;

    @Inject
    @NonNull
    PreferencesManager preferencesManager;
    @Inject
    @NonNull
    NewsInteractor interactor;
    @Inject
    @NonNull
    NotificationHelper notificationHelper;
    @Nullable
    private Disposable disposable;

    public static void start(@NonNull Context context) {
        final Intent intent = new Intent(context, NewsUpdateService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @NonNull
    public static PendingIntent getStopPendingIntent(@NonNull Context context) {
        final Intent stopSelfIntent = new Intent(context, NewsUpdateService.class);
        stopSelfIntent.setAction(ACTION_STOP_NEWS_UPDATE);
        return PendingIntent.getService(context, 0, stopSelfIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null && ACTION_STOP_NEWS_UPDATE.equals(intent.getAction())) {
            Log.d(LOG_TAG, "called to cancel service");
            notificationHelper.cancelNotification(NOTIFICATION_NEWS_UPDATE_ID);
            stopSelf();
            return START_STICKY;
        }

        final Category currentCategory = preferencesManager.getCurrentCategory();
        disposable = interactor.loadNews(currentCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processLoading,
                        this::handleError);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DI.getUpdateServiceComponent(this).inject(this);

        final Notification notification = notificationHelper.getNewsUpdateNotification();
        startForeground(NOTIFICATION_NEWS_UPDATE_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.disposeSafely(disposable);
    }

    private void processLoading() {
        notificationHelper.showResultNotification(NOTIFICATION_NEWS_UPDATE_ID, getString(R.string.service_result_success));
        stopForeground(false);
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        notificationHelper.showResultNotification(NOTIFICATION_NEWS_UPDATE_ID, getString(R.string.service_result_fail));
        stopForeground(false);
    }
}
