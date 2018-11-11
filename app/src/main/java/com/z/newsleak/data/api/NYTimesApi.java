package com.z.newsleak.data.api;

import com.z.newsleak.model.network.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NYTimesApi {

    @GET("{section}.json")
    Single<NewsResponse> getNews(@Path("section") String section);
}
