package com.example.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @GET("youtube/v3/channels")
    Call<JsonObject> get(
            @Query("forUsername") String forUsername,
            @Query("part") String part,
            @Query("key") String key
    );

    @GET
    Call<ResponseBody> getImage(@Url String url);

    @GET("photos")
    Call<JsonArray> getPhotos();
}
