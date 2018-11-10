package com.z.newsleak.features.newsfeed;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.z.newsleak.data.Category;
import com.z.newsleak.network.api.RestApi;
import com.z.newsleak.utils.SupportUtils;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter extends MvpBasePresenter<NewsListContract.View> implements NewsListContract.Presenter {

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    private Category currentCategory = null;

    public void loadNews(@NonNull Category category) {

        if (category.equals(currentCategory)) {
            return;
        }

        final Disposable searchDisposable = RestApi.getInstance()
                .getApi()
                .getNews(category.getSection())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> ifViewAttached(NewsListContract.View::showLoading))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> ifViewAttached(view -> view.processResponse(response)),
                        th -> ifViewAttached(view -> view.handleError(th)));
        compositeDisposable.add(searchDisposable);

        currentCategory = category;
    }

    @Override
    public void destroy() {
        super.destroy();
        SupportUtils.disposeSafely(compositeDisposable);
    }

}
