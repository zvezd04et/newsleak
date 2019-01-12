package com.z.newsleak.di.modules;

import android.app.Service;
import android.content.Context;

import com.z.newsleak.di.scopes.UpdateServiceScope;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class UpdateServiceModule {

    @NonNull
    private final Service service;

    public UpdateServiceModule(@NonNull Service service) {
        this.service = service;
    }

    @Provides
    @UpdateServiceScope
    @NonNull
    Service provideService() {
        return service;
    }

    @Provides
    @UpdateServiceScope
    @NonNull
    Context provideServiceContext() {
        return service.getBaseContext();
    }
}
