package com.z.newsleak.data.db;

import com.z.newsleak.model.db.NewsEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    Observable<List<NewsEntity>> getAll();

    @Query("SELECT * FROM news WHERE section = :section")
    Single<List<NewsEntity>> getNewsBySection(String section);

    @Query("SELECT * FROM news WHERE id = :id")
    Single<NewsEntity> getNewsById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NewsEntity> news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(NewsEntity newsEntity);

    @Query("DELETE FROM news")
    void deleteAll();

    @Query("DELETE FROM news WHERE section = :section")
    void deleteBySection(String section);

    @Query("DELETE FROM news WHERE id = :id")
    void deleteById(int id);
}
