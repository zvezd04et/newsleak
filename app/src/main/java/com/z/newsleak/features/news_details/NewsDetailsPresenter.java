package com.z.newsleak.features.news_details;

import com.z.newsleak.features.base.BaseNewsItemPresenter;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsPresenter extends BaseNewsItemPresenter<NewsDetailsContract.View> implements NewsDetailsContract.Presenter {

    public NewsDetailsPresenter(int id) {
        super(id);
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
    }

    private void processDeleting() {
        ifViewAttached(NewsDetailsContract.View::close);
    }
}
