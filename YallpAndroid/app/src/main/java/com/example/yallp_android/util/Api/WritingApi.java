package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.CompletedWritings;
import com.example.yallp_android.models.WritingListElement;

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
    @GET("getJson/{languageId}")
    Call<WritingListElement[]> getWritingListForLanguage(@Header("Authorization") String token, @Path("languageId") int languageId);

    @Headers({"Content-Type: application/json"})
    @GET("read/{writingId}")
    Call<WritingListElement> readDetailsOfOneWriting(
            @Header("Authorization") String token,
            @Path("writingId") int writingId);


}
