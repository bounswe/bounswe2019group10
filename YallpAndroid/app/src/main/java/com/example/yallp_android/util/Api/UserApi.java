package com.example.yallp_android.util.Api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import com.example.yallp_android.models.SignUpUser;

public interface UserApi {

    @Headers({"Content-Type: application/json"})
    @POST("signup")
    Call<ResponseBody> signup(
            @Body SignUpUser signupUser
    );

}
