package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.QuizListElement;
import com.example.yallp_android.models.UserListElement;
import com.example.yallp_android.models.WritingListElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SearchApi {

    @Headers({"Content-Type: application/json"})
    @POST("quiz/{languageId}/{term}")
    Call<QuizListElement[]> searchQuiz(@Header("Authorization") String token, @Path("languageId") int languageId, @Path("term") String term);

    @Headers({"Content-Type: application/json"})
    @POST("writing/{languageId}/{term}")
    Call<WritingListElement[]> searchWriting(@Header("Authorization") String token, @Path("languageId") int languageId, @Path("term") String term);

    @Headers({"Content-Type: application/json"})
    @GET("member/{username}")
    Call<UserListElement[]> searchUser(@Header("Authorization") String token,@Path("username") String term);
}
