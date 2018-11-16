package com.z.newsleak.data.db;

import com.z.newsleak.model.db.NewsEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "newsleak.db";

    public abstract NewsDao getNewsDao();
}