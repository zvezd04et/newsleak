package com.z.newsleak.network.api;

import com.z.newsleak.network.dto.NewsDTO;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsEndpoint {

    @GET("{section}.json")
    Single<NewsDTO> getNews(@Path("section") String section);
}
