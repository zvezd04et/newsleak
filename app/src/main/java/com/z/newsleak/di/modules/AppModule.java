package com.z.newsleak.di.modules;

import android.content.Context;

import com.z.newsleak.di.qualifiers.ForApplication;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context appContext;

    public AppModule(@NonNull Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @Singleton
    @ForApplication
    @NonNull
    Context provideContext() {
        return appContext;
    }
}