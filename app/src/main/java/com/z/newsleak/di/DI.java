package com.z.newsleak.di;

import android.app.Service;
import android.content.Context;

import com.z.newsleak.di.components.AppComponent;
import com.z.newsleak.di.components.DaggerAppComponent;
import com.z.newsleak.di.components.NewsItemComponent;
import com.z.newsleak.di.components.UpdateServiceComponent;
import com.z.newsleak.di.modules.AppModule;
import com.z.newsleak.di.modules.NewsItemModule;
import com.z.newsleak.di.modules.UpdateServiceModule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DI {

    @Nullable
    private static AppComponent appComponent;

    public static void init(@NonNull Context appContext) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(appContext))
                .build();
    }

    @NonNull
    public static AppComponent getAppComponent() {
        checkInit();
        return appComponent;
    }

    @NonNull
    public static NewsItemComponent getNewsItemComponent(int id) {
        checkInit();
        return appComponent.plus(new NewsItemModule(id));
    }

    @NonNull
    public static UpdateServiceComponent getUpdateServiceComponent(Service service) {
        checkInit();
        return appComponent.plus(new UpdateServiceModule(service));
    }

    private static void checkInit() {
        if (appComponent == null) {
            throw new RuntimeException("DI was not initialized correctly. You should " +
                    "initialize DI by calling method DI.init().");
        }
    }
}
