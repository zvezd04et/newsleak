package com.z.newsleak.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.z.newsleak.App;
import com.z.newsleak.R;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.data.api.NYTimesApi;
import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.features.main.MainActivity;
import com.z.newsleak.model.Category;
import com.z.newsleak.utils.NetworkUtils;
import com.z.newsleak.utils.NewsTypeConverters;
import com.z.newsleak.utils.SupportUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsUpdateService extends Service {

    private static final String LOG_TAG = "NewsUpdateService";
    private static final String CHANNEL_NEWS_UPDATE_ID = "CHANNEL_NEWS_UPDATE_ID";
    private static final String ACTION_STOP_NEWS_UPDATE = "com.z.newsleak.STOP_SELF";
    private static final int NOTIFICATION_NEWS_UPDATE_ID = 25;
    private static final int TIMEOUT_IN_MINUTES = 1;

    @Nullable
    private Disposable disposable;
    @NonNull
    private final PreferencesManager preferencesManager = App.getPreferencesManager();
    @NonNull
    private final NewsRepository repository = App.getRepository();
    @NonNull
    NetworkUtils networkUtils = NetworkUtils.getInstance();
    @NonNull
    private PendingIntent contentIntent;

    @Inject
    @NonNull
    NYTimesApi api;

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, NewsUpdateService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    private static PendingIntent getStopPendingIntent(Context context) {
        final Intent stopSelfIntent = new Intent(context, NewsUpdateService.class);
        stopSelfIntent.setAction(ACTION_STOP_NEWS_UPDATE);
        return PendingIntent.getService(context, 0, stopSelfIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        if (intent != null && ACTION_STOP_NEWS_UPDATE.equals(intent.getAction())) {
            Log.d(LOG_TAG, "called to cancel service");
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(NOTIFICATION_NEWS_UPDATE_ID);
            stopSelf();
        }

        disposable = networkUtils.getOnlineNetwork()
                .timeout(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES)
                .flatMapCompletable(aBoolean -> updateNews())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            showResultNotification(getString(R.string.service_result_success));
                            stopForeground(false);
                        },
                        th -> {
                            Log.e(LOG_TAG, th.getMessage(), th);
                            showResultNotification(getString(R.string.service_result_fail));
                            stopForeground(false);
                        });

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        App.getNetworkComponent().inject(this);

        contentIntent = MainActivity.getPendingIntent(this);

        final PendingIntent stopPendingIntent = getStopPendingIntent(this);

        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_NEWS_UPDATE_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.news_update_channel_name))
                .setContentText(getString(R.string.news_update_notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .addAction(R.drawable.ic_error_outline, getString(R.string.service_action_cancel), stopPendingIntent)
                .build();

        startForeground(NOTIFICATION_NEWS_UPDATE_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SupportUtils.disposeSafely(disposable);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.news_update_channel_name);
            String description = getString(R.string.news_update_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_NEWS_UPDATE_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showResultNotification(@NonNull String message) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_NEWS_UPDATE_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.news_update_channel_name))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_NEWS_UPDATE_ID, notification);
        }
    }

    private Completable updateNews() {
        final Category currentCategory = preferencesManager.getCurrentCategory();

        return api.getNews(currentCategory.getSection())
                .map(response -> NewsTypeConverters.convertFromNetworkToDb(response.getResults(), currentCategory))
                .flatMapCompletable(repository::saveData);
    }
}
