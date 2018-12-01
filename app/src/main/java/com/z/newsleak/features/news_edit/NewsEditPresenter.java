package com.z.newsleak.features.news_edit;

import com.arellomobile.mvp.InjectViewState;
import com.z.newsleak.features.base.BaseNewsItemPresenter;
import com.z.newsleak.model.NewsEditItem;
import com.z.newsleak.model.NewsItem;

import java.util.Calendar;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsEditPresenter extends BaseNewsItemPresenter<NewsEditView> {

    @NonNull
    private Calendar calendar = Calendar.getInstance();

    public NewsEditPresenter(int id) {
        super(id);
    }

    @Override
    protected void processLoading(@NonNull NewsItem newsItem) {
        super.processLoading(newsItem);

        calendar.setTime(newsItem.getPublishedDate());
        getViewState().setCalendar(calendar);
    }

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
        getViewState().close();
    }

}
