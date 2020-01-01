package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.QuizAnswers;
import com.example.yallp_android.models.QuizListElement;
import com.example.yallp_android.models.QuizResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuizApi {


    @Headers({"Content-Type: application/json"})
    @GET("quiz/{quizId}")
    Call<QuizListElement> getQuiz(
            @Header("Authorization") String token,
            @Path("quizId") int quizId);

    @Headers({"Content-Type: application/json"})
    @POST("quiz/{quizId}/submit")
    Call<QuizResult> postQuizResult(
            @Header("Authorization") String token,
            @Body QuizAnswers quizAnswers,
            @Path("quizId") int quizId
    );

    @Headers({"Content-Type: application/json"})
    @GET("quiz/levelorlower/{level}/language/{languageId}")
    Call<QuizListElement[]> getQuizForLevelOrLowerAndLanguage(
            @Header("Authorization") String token,
            @Path("level") int level,
            @Path("languageId") int languageId);

}