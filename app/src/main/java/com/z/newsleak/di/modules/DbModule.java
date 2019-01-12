package com.z.newsleak.di.modules;

import android.content.Context;

import com.z.newsleak.Constant;
import com.z.newsleak.data.db.AppDatabase;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.di.qualifiers.ForApplication;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    @NonNull
    AppDatabase provideDatabase(@ForApplication @NonNull Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, Constant.DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    @NonNull
    NewsDao provideNewsDao(@NonNull AppDatabase database) {
        return database.getNewsDao();
    }
}
