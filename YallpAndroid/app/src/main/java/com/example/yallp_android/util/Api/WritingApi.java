package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.CompletedWritings;
import com.example.yallp_android.models.ImageUrl;
import com.example.yallp_android.models.NonCompletedAssignments;
import com.example.yallp_android.models.Token;
import com.example.yallp_android.models.WritingExerciseElement;
import com.example.yallp_android.models.WritingListElement;
import com.example.yallp_android.models.WritingRequest;
import com.example.yallp_android.models.WritingRequestWithUrl;
import com.example.yallp_android.models.WritingSuggestion;
import com.example.yallp_android.models.WritingSuggestionResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Headers({"Content-Type: application/json"})
    @GET("nonCompletedAssignments")
    Call<NonCompletedAssignments[]> nonCompletedAssignments(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST("score/{writingResultId}")
    Call<CompletedWritings> giveScoreForWriting(
            @Header("Authorization") String token,
            @Path("writingResultId") int writingResultId,
            @Body int score);

    @Headers({"Content-Type: application/json"})
    @GET("{writingId}")
    Call<WritingExerciseElement> getWritingExercise(
            @Header("Authorization") String token,
            @Path("writingId") int writingId);

    @Headers({"Content-Type: application/json"})
    @POST("{writingId}/submit")
    Call<Token> submitWriting(
            @Header("Authorization") String token,
            @Path("writingId") int writingId,
            @Body WritingRequest writingRequest);


    @Headers({"Content-Type: application/json"})
    @POST("{writingId}/submitWithImageURL")
    Call<Token> submitWritingWithImageUrl(
            @Header("Authorization") String token,
            @Path("writingId") int writingId,
            @Body WritingRequestWithUrl writingRequest);


    @Multipart
    @POST("uploadWritingImage")
    Call<ImageUrl> writingImageSubmit(
            @Header("Authorization") String token,
            @Part MultipartBody.Part file
    );


    @Headers({"Content-Type: application/json"})
    @POST("add")
    Call<WritingSuggestionResponse> suggestWriting(
            @Header("Authorization") String token,
            @Body WritingSuggestion suggestion);
}
