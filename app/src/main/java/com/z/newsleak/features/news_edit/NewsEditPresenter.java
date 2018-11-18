package com.z.newsleak.features.news_edit;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.z.newsleak.App;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.SupportUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsEditPresenter extends MvpBasePresenter<NewsEditContract.View> implements NewsEditContract.Presenter{

    private static final String LOG_TAG = "NewsEditPresenter";

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();
    @Nullable
    private NewsItem newsItem;

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
    public void saveData() {

        if (newsItem != null) {
            return;
        }

        ifViewAttached(view -> view.updateData(newsItem));

        final Disposable disposable = database.insert(newsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processSaving, this::handleError);
        compositeDisposable.add(disposable);
    }

    private void processLoading(@NonNull NewsItem newsItem) {
        ifViewAttached(view -> view.setData(newsItem));
        this.newsItem = newsItem;
    }

    private void processSaving() {
        ifViewAttached(NewsEditContract.View::close);
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        ifViewAttached(NewsEditContract.View::close);
    }

}
