package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.LoginUserWithEmail;
import com.example.yallp_android.models.LoginUserWithName;
import com.example.yallp_android.models.SignUpUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi {

    @Headers({"Content-Type: application/json"})
    @POST("register")
    Call<ResponseBody> signup(
            @Body SignUpUser signupUser
    );

    @Headers({"Content-Type: application/json"})
    @POST("authenticate")
    Call<ResponseBody> loginWithName(
            @Body LoginUserWithName loginUserWithName
    );

    @Headers({"Content-Type: application/json"})
    @POST("authenticate")
    Call<ResponseBody> loginWithEmail(
            @Body LoginUserWithEmail loginUserWithEmail
    );

}
