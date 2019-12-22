package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.UserReportCause;
import com.example.yallp_android.models.UserReportSubmit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserReportApi {

    @Headers({"Content-Type: application/json"})
    @GET("causes")
    Call<UserReportCause[]> getUserReportCauses(@Header("Authorization") String token);


    @Headers({"Content-Type: application/json"})
    @POST("send")
    Call<UserReportSubmit> sendReport(
            @Header("Authorization") String token,
            @Body UserReportSubmit report
    );
}
