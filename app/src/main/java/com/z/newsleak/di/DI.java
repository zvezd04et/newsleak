package com.z.newsleak.di;

import android.content.Context;

import com.z.newsleak.di.components.AppComponent;
import com.z.newsleak.di.components.DaggerAppComponent;
import com.z.newsleak.di.components.NewsItemComponent;
import com.z.newsleak.di.modules.AppModule;
import com.z.newsleak.di.modules.NewsItemModule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DI {

    @Nullable
    private static Context context;

    @Nullable
    private static AppComponent appComponent;

    public static void init(Context appContext) {
        context = appContext;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }

    @NonNull
    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            throw new RuntimeException("DI was not initialized correctly. You should " +
                    "initialize DI by calling method DI.init().");
        }
        return appComponent;
    }

    @NonNull
    public static NewsItemComponent getNewsItemComponent(int id) {
        if (appComponent == null) {
            throw new RuntimeException("DI was not initialized correctly. You should " +
                    "initialize DI by calling method DI.init().");
        }
        return appComponent.plus(new NewsItemModule(id));
    }
}
