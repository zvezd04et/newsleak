package com.z.newsleak.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.z.newsleak.App;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.data.api.NYTimesApiProvider;
import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.model.Category;
import com.z.newsleak.utils.NewsTypeConverters;
import com.z.newsleak.utils.SupportUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsUpdateService extends Service {

    private static final String LOG_TAG = "NewsUpdateService";

    @Nullable
    private Disposable disposable;
    @NonNull
    private final PreferencesManager preferencesManager = App.getPreferencesManager();
    @NonNull
    private final NewsRepository repository = App.getRepository();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Category currentCategory = preferencesManager.getCurrentCategory();

        disposable = NYTimesApiProvider.getInstance()
                .createApi()
                .getNews(currentCategory.getSection())
                .map(response -> NewsTypeConverters.convertFromNetworkToDb(response.getResults(), currentCategory))
                .flatMapCompletable(repository::saveData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> stopForeground(false),
                        th -> {
                            Log.e(LOG_TAG, th.getMessage(), th);
                            stopForeground(false);
                        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SupportUtils.disposeSafely(disposable);
    }
}
