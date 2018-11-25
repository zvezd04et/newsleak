package com.z.newsleak.features.base;

import android.util.Log;

import com.z.newsleak.model.NewsItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaseNewsItemPresenter<V extends BaseNewsItemView> extends BasePresenter<V> {

    private static final String LOG_TAG = "NewsEditPresenter";

    @Nullable
    protected NewsItem newsItem;
    protected int id;

    protected BaseNewsItemPresenter(int id) {
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getData();
    }

    private void getData() {

        if (newsItem != null) {
            return;
        }

        final Disposable disposable = database.getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processLoading, this::handleError);
        compositeDisposable.add(disposable);
    }

    private void processLoading(@NonNull NewsItem newsItem) {
        ifViewAttached(view -> view.setData(newsItem));
        this.newsItem = newsItem;
    }

    protected void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        ifViewAttached(BaseNewsItemView::close);
    }
}
