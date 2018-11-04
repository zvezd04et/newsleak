package com.z.newsleak.network.api;

import com.z.newsleak.network.NewsResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsEndpoint {

    @GET("{section}.json")
    Single<Response<NewsResponse>> getNews(@Path("section") String section);
}
