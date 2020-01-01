package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.Language;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface LanguageApi {

    @Headers({"Content-Type: application/json"})
    @GET("lang/unsubs")
    Call<Language[]> getUnsubsLanguages(@Header("Authorization") String token);
}
