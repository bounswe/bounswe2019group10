package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.Quiz;
import com.example.yallp_android.models.QuizAnswers;
import com.example.yallp_android.models.QuizResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface QuizApi {


    @Headers({"Content-Type: application/json"})
    @GET("quiz/1")
    Call<Quiz> getQuiz(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST("quiz/1/submit")
    Call<QuizResult> postQuizResult(
            @Header("Authorization") String token,
            @Body QuizAnswers quizAnswers
    );

}
