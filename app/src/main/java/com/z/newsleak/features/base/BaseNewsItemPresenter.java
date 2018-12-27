package com.z.newsleak.features.base;

import android.util.Log;

import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.model.NewsItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseNewsItemPresenter<V extends BaseNewsItemView> extends BasePresenter<V> {

    private static final String LOG_TAG = "BaseNewsItemPresenter";

    @NonNull
    protected NewsRepository repository;// = App.getRepository();

    @Nullable
    protected NewsItem newsItem;
    protected int id;

    public BaseNewsItemPresenter(int id, @NonNull NewsRepository repository) {
        this.id = id;
        this.repository = repository;
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

        final Disposable disposable = repository.getItemObservable(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processLoading, this::handleError);
        compositeDisposable.add(disposable);
    }

    protected void processLoading(@NonNull NewsItem newsItem) {
        getViewState().setData(newsItem);
        this.newsItem = newsItem;
    }

    protected void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        getViewState().close();
    }
}
