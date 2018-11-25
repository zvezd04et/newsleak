package com.z.newsleak.features.newsfeed;

import android.util.Log;

import com.z.newsleak.features.base.BasePresenter;
import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadState;
import com.z.newsleak.data.api.NYTimesApiProvider;
import com.z.newsleak.utils.NewsTypeConverters;
import com.z.newsleak.utils.SupportUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter extends BasePresenter<NewsListContract.View> implements NewsListContract.Presenter {

    private static final String LOG_TAG = "NewsListPresenter";

    @Nullable
    private Disposable disposable;

    @Nullable
    private Category currentCategory = null;

    @NonNull
    private List<NewsItem> newsList = new ArrayList<>();

    @Override
    public void destroy() {
        super.destroy();
        SupportUtils.disposeSafely(disposable);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        final Disposable disposable = database.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processNews,
                        this::handleError);

        compositeDisposable.add(disposable);
    }

    @Override
    public void loadNews(@NonNull Category category) {

        showViewState(LoadState.LOADING);

        disposable = NYTimesApiProvider.getInstance()
                .createApi()
                .getNews(category.getSection())
                .map(response -> NewsTypeConverters.convertFromNetworkToDb(response.getResults(), currentCategory))
                .flatMapCompletable(this::saveData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> ifViewAttached(view -> view.showNews(newsList)),
                        this::handleError);
    }

    public void onSpinnerCategorySelected(@Nullable Category category) {
        if (category == null) {
            return;
        }
        if (category.equals(currentCategory)) {
            return;
        }
        loadNews(category);
        currentCategory = category;
    }

    private void processNews(@Nullable List<NewsItem> news) {

        if (news == null || news.isEmpty()) {
            showViewState(LoadState.HAS_NO_DATA);
            return;
        }

        newsList = news;
        ifViewAttached(view -> view.showNews(newsList));
    }

    private Completable saveData(final List<NewsItem> newsList) {
        return Completable.fromCallable((Callable<Void>) () -> {
            database.deleteAll();
            database.insertAll(newsList);

            return null;
        });
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        showViewState(LoadState.ERROR);
    }

    private void showViewState(LoadState state) {
        ifViewAttached(view -> view.showState(state));
    }
}
