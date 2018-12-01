package com.z.newsleak.features.news_details;

import com.arellomobile.mvp.InjectViewState;
import com.z.newsleak.features.base.BaseNewsItemPresenter;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsDetailsPresenter extends BaseNewsItemPresenter<NewsDetailsView> {

    public NewsDetailsPresenter(int id) {
        super(id);
    }

    public void onDeleteSelected() {
        Disposable disposable = Completable.fromCallable((Callable<Void>) () -> {
            database.deleteById(id);
            return null;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processDeleting, this::handleError);
        compositeDisposable.add(disposable);
    }

    public void onEditSelected() {
        getViewState().openEditorActivity(id);
    }

    private void processDeleting() {
        getViewState().close();
    }

}
