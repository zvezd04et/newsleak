package com.z.newsleak.di.modules;

import android.content.Context;
import android.net.ConnectivityManager;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    @NonNull
    public Context provideContext() {
        return appContext;
    }
}