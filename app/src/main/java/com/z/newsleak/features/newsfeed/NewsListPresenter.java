package com.z.newsleak.features.newsfeed;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.z.newsleak.data.Category;
import com.z.newsleak.network.api.RestApi;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter extends MvpBasePresenter<NewsListView> {

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(@NonNull NewsListView view) {
        super.attachView(view);
    }

    public void loadNews(@NonNull Category category) {

        final Disposable searchDisposable = RestApi.getInstance()
                .getApi()
                .getNews(category.getSection())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> ifViewAttached(NewsListView::showLoading))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> ifViewAttached(view -> view.processResponse(response)),
                        th -> ifViewAttached(view -> view.handleError(th)));
        compositeDisposable.add(searchDisposable);
    }
}
