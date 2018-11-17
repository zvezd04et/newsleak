package com.z.newsleak.features.newsfeed;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.z.newsleak.App;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.model.db.NewsEntity;
import com.z.newsleak.ui.LoadState;
import com.z.newsleak.data.api.NYTimesApiProvider;
import com.z.newsleak.utils.NewsItemConverter;
import com.z.newsleak.utils.SupportUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter extends MvpBasePresenter<NewsListContract.View> implements NewsListContract.Presenter {

    private static final String LOG_TAG = "NewsListPresenter";

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Nullable
    private Disposable disposable;

    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();

    @Nullable
    private Category currentCategory = null;

    @NonNull
    private List<NewsItem> newsList = new ArrayList<>();

    @Override
    public void destroy() {
        super.destroy();
        SupportUtils.disposeSafely(compositeDisposable);
        SupportUtils.disposeSafely(disposable);
    }

    public NewsListPresenter() {

        final Disposable disposable = database.getAll()
                .map(newsEntities -> NewsItemConverter.convertFromDb(newsEntities, currentCategory))
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
                .map(response -> NewsItemConverter.convertFromNetworkToDb(response.getResults()))
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

    public Completable saveData(final List<NewsEntity> newsList) {
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
