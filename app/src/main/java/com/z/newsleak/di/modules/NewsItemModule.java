package com.z.newsleak.di.modules;

import com.z.newsleak.di.scopes.NewsItemScope;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsItemModule {

    private final int currentItemId;

    public NewsItemModule(int currentItemId) {
        this.currentItemId = currentItemId;
    }

    @Provides
    @NewsItemScope
    public int provideNewsItemId() {
        return currentItemId;
    }
}
