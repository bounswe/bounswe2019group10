package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.LoginUserWithEmail;
import com.example.yallp_android.models.LoginUserWithName;
import com.example.yallp_android.models.SignUpUser;
import com.example.yallp_android.models.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi {

    @Headers({"Content-Type: application/json"})
    @POST("register")
    Call<Token> signup(
            @Body SignUpUser signupUser
    );

    @Headers({"Content-Type: application/json"})
    @POST("authenticate")
    Call<Token> loginWithName(
            @Body LoginUserWithName loginUserWithName
    );

    @Headers({"Content-Type: application/json"})
    @POST("authenticate")
    Call<Token> loginWithEmail(
            @Body LoginUserWithEmail loginUserWithEmail
    );

}
