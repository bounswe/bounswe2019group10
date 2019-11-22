package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.CompletedWritings;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface WritingApi {

    @Headers({"Content-Type: application/json"})
    @GET("scores")
    Call<CompletedWritings[]> getCompletedWritingExercises(@Header("Authorization") String token);
}
