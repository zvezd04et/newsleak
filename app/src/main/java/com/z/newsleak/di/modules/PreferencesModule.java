package com.z.newsleak.di.modules;

import android.content.Context;

import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.di.qualifiers.ForApplication;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {

    @Provides
    @Singleton
    @NonNull
    PreferencesManager providePreferencesManager(@ForApplication Context context) {
        return new PreferencesManager(context);
    }
}
