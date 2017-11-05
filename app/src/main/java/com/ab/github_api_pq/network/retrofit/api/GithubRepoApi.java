package com.ab.github_api_pq.network.retrofit.api;


import com.ab.github_api_pq.model.GithubRepoModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GithubRepoApi {
    @GET("users/JakeWharton/repos?per_page=15")
    Observable<List<GithubRepoModel>> getRepoModel(@Query("page") int page);
}
