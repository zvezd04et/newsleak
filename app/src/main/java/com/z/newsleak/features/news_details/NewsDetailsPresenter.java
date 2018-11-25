package com.z.newsleak.features.news_details;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.z.newsleak.App;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.SupportUtils;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsPresenter extends MvpBasePresenter<NewsDetailsContract.View> implements NewsDetailsContract.Presenter {

    private static final String LOG_TAG = "NewsEditPresenter";

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();
    @Nullable
    private NewsItem newsItem;
    private int id;

    @Override
    public void destroy() {
        super.destroy();
        SupportUtils.disposeSafely(compositeDisposable);
    }

    @Override
    public void getData(int id) {

        if (newsItem != null) {
            return;
        }

        final Disposable disposable = database.getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processLoading, this::handleError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteData() {
        Disposable disposable = Completable.fromCallable((Callable<Void>) () -> {
            database.deleteById(id);
            return null;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processDeleting, this::handleError);

        compositeDisposable.add(disposable);

        compositeDisposable.add(disposable);
    }

    private void processLoading(@NonNull NewsItem newsItem) {
        ifViewAttached(view -> view.setData(newsItem));
        this.newsItem = newsItem;
        id = newsItem.getId();
    }

    private void processDeleting() {
        ifViewAttached(NewsDetailsContract.View::close);
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        ifViewAttached(NewsDetailsContract.View::close);
    }
}
