package com.z.newsleak.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import com.z.newsleak.R;
import com.z.newsleak.features.main.MainActivity;
import com.z.newsleak.service.NewsUpdateService;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;

public class NotificationHelper extends ContextWrapper {

    private static final String NEWS_UPDATE_CHANNEL = "NEWS_UPDATE_CHANNEL";

    @Nullable
    private NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    @Inject
    public NotificationHelper(Context base) {
        super(base);
        createNotificationChannel();
    }

    public Notification getNewsUpdateNotification() {
        final PendingIntent stopPendingIntent = NewsUpdateService.getStopPendingIntent(this);
        return createNotificationBuilder(getString(R.string.news_update_notification_text), false)
                .addAction(R.drawable.ic_error_outline, getString(R.string.service_action_cancel), stopPendingIntent)
                .build();
    }

    public void showResultNotification(int notificationId, @NonNull String message) {
        final Builder notification = createNotificationBuilder(message, true);
        showNotification(notificationId, notification);
    }

    public void cancelNotification(int notificationId) {
        if (manager != null) {
            manager.cancel(notificationId);
        }
    }

    private void showNotification(int notificationId, Builder notification) {
        if (manager != null) {
            manager.notify(notificationId, notification.build());
        }
    }

    private void createNotificationChannel() {
        if (manager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final CharSequence name = getString(R.string.news_update_channel_name);
            final String description = getString(R.string.news_update_channel_description);
            final int importance = NotificationManager.IMPORTANCE_DEFAULT;
            final NotificationChannel channel = new NotificationChannel(NEWS_UPDATE_CHANNEL, name, importance);
            channel.setDescription(description);
            manager.createNotificationChannel(channel);
        }
    }

    private Builder createNotificationBuilder(@NonNull String message, boolean autoCancel) {
        final PendingIntent contentIntent = MainActivity.getPendingIntent(this);
        return new Builder(this, NEWS_UPDATE_CHANNEL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.news_update_channel_name))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(autoCancel);
    }
}
