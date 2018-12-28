package com.z.newsleak.di.modules;

import com.z.newsleak.BuildConfig;
import com.z.newsleak.Constant;
import com.z.newsleak.data.api.ApiKeyInterceptor;
import com.z.newsleak.data.api.NYTimesApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final int TIMEOUT_IN_SECONDS = 15;

    @Provides
    @Singleton
    @NonNull
    public OkHttpClient provideOkHttpClient() {

        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor networkLogInterceptor = new HttpLoggingInterceptor();
            networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create(Constant.API_KEY))
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @NonNull
    public Retrofit provideRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constant.API_HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @NonNull
    public NYTimesApi provideNYTimesApi(Retrofit retrofit) {
        return retrofit.create(NYTimesApi.class);
    }
}
