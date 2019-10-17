package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.Quiz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface QuizApi {


    @Headers({"Content-Type: application/json"})
    @GET("quiz/1")
    Call<Quiz> getQuiz(@Header("Authorization") String token);

}
