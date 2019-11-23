package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.CompletedWritings;
import com.example.yallp_android.models.ReadWritingContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface WritingApi {

    @Headers({"Content-Type: application/json"})
    @GET("scores")
    Call<CompletedWritings[]> getCompletedWritingExercises(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @GET("read/{writingId}")
    Call<ReadWritingContent> readDetailsOfOneWriting(
            @Header("Authorization") String token,
            @Path("writingId") int writingId);
}
