package com.z.newsleak.di.modules;

import android.content.Context;

import com.z.newsleak.di.scopes.NewsUpdateScope;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context appContext;

    public AppModule(@NonNull Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @NewsUpdateScope
    @NonNull
    public Context provideContext() {
        return appContext;
    }
}