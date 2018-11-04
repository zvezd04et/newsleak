package com.z.newsleak.network.api;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class ApiKeyInterceptor implements Interceptor {

    private static final String PARAM_API_KEY = "api_key";

    private final String apiKey;

    private ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }


    public static Interceptor create(@NonNull String apiKey) {
        return new ApiKeyInterceptor(apiKey);
    }


    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request requestWithoutApiKey = chain.request();

//        if (requestWithoutApiKey.body() == null || requestWithoutApiKey.header("Content-Encoding") != null) {
//            return chain.proceed(requestWithoutApiKey);
//        }

        final HttpUrl url = requestWithoutApiKey.url()
                .newBuilder()
                .addQueryParameter(PARAM_API_KEY, apiKey)
                .build();

        final Request requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
                .url(url)
                .build();

        return chain.proceed(requestWithAttachedApiKey);
    }
}
