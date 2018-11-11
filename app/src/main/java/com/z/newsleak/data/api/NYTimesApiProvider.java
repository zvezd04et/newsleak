package com.z.newsleak.data.api;

import com.z.newsleak.BuildConfig;
import com.z.newsleak.Constant;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NYTimesApiProvider {

    private static final int TIMEOUT_IN_SECONDS = 15;
    private static NYTimesApiProvider apiProvider;

    private final NYTimesApi Api;

    public static synchronized NYTimesApiProvider getInstance() {
        if (apiProvider == null) {
            apiProvider = new NYTimesApiProvider();
        }
        return apiProvider;
    }

    private NYTimesApiProvider() {
        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);

        //init endpoints here. It's can be more then one endpoint
        Api = retrofit.create(NYTimesApi.class);
    }

    @NonNull
    private Retrofit buildRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constant.API_HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private OkHttpClient buildOkHttpClient() {

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

    public NYTimesApi createApi() {
        return Api;
    }


}
