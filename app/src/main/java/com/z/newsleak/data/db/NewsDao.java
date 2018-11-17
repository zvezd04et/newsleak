package com.z.newsleak.data.db;

import com.z.newsleak.model.NewsItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news ORDER BY published_date DESC")
    Observable<List<NewsItem>> getAll();

    @Query("SELECT * FROM news WHERE section = :section")
    Observable<List<NewsItem>> getNewsBySection(String section);

    @Query("SELECT * FROM news WHERE id = :id")
    Observable<NewsItem> getNewsById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NewsItem> news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(NewsItem newsItem);

    @Query("DELETE FROM news")
    void deleteAll();

    @Query("DELETE FROM news WHERE section = :section")
    void deleteBySection(String section);

    @Query("DELETE FROM news WHERE id = :id")
    void deleteById(int id);
}
