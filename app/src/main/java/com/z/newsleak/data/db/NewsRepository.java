package com.z.newsleak.data.db;

import com.z.newsleak.model.NewsItem;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class NewsRepository {

    @NonNull
    private NewsDao newsDao;

    @Inject
    public NewsRepository(@NonNull NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    public Completable saveData(final List<NewsItem> newsList) {
        return Completable.fromCallable((Callable<Void>) () -> {
            newsDao.deleteAll();
            newsDao.insertAll(newsList);

            return null;
        });
    }

    public Completable saveItem(final NewsItem newsItem) {
        return newsDao.update(newsItem);
    }

    public Observable<List<NewsItem>> getDataObservable() {
        return newsDao.getAll();
    }

    public Observable<NewsItem> getItemObservable(int id) {
        return newsDao.getNewsById(id);
    }

    public Completable deleteItem(int id) {
        return Completable.fromCallable((Callable<Void>) () -> {

            newsDao.deleteById(id);
            return null;
        });
    }
}
