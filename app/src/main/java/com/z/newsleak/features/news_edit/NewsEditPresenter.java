package com.z.newsleak.features.news_edit;

import com.z.newsleak.features.base.BaseNewsItemPresenter;
import com.z.newsleak.model.NewsEditItem;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsEditPresenter extends BaseNewsItemPresenter<NewsEditContract.View> implements NewsEditContract.Presenter {

    public NewsEditPresenter(int id) {
        super(id);
    }

    @Override
    public void saveData(@NonNull NewsEditItem newsEditItem) {

        if (newsItem == null) {
            return;
        }

        newsItem.setTitle(newsEditItem.getTitle());
        newsItem.setPreviewText(newsEditItem.getPreviewText());
        newsItem.setUrl(newsEditItem.getUrl());
        newsItem.setNormalImageUrl(newsEditItem.getNormalImageUrl());
        newsItem.setPublishedDate(newsEditItem.getPublishedDate());

        final Disposable disposable = database.update(newsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processSaving, this::handleError);
        compositeDisposable.add(disposable);
    }


    private void processSaving() {
        ifViewAttached(NewsEditContract.View::close);
    }

}
