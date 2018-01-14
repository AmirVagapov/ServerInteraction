package com.vagapov.amir.serverinteractionexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RestAPIforUser {

    @GET("users/{user}")
    Call<RetrofitModel> loadUser(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<RetrofitReposModel>> loadRepos (@Path("user") String user);
}
