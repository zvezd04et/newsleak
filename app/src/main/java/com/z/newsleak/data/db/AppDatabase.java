package com.z.newsleak.data.db;

import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.NewsTypeConverters;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
@TypeConverters(NewsTypeConverters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NewsDao getNewsDao();
}