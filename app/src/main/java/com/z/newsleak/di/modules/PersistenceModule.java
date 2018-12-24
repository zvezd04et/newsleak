package com.z.newsleak.di.modules;

import android.content.Context;

import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.data.db.AppDatabase;
import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.di.scopes.NewsUpdateScope;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    @Provides
    @NewsUpdateScope
    @NonNull
    AppDatabase provideDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    @Provides
    @NewsUpdateScope
    @NonNull
    NewsRepository provideNewsRepository(AppDatabase appDatabase) {
        return NewsRepository.getInstance(appDatabase);
    }

    @Provides
    @NewsUpdateScope
    @NonNull
    PreferencesManager providePreferencesManager(Context context) {
        return PreferencesManager.getInstance(context);
    }

}
