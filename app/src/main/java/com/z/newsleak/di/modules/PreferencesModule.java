package com.z.newsleak.di.modules;

import android.content.Context;

import com.z.newsleak.data.PreferencesManager;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {

    @Provides
    @Singleton
    @NonNull
    PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }
}
