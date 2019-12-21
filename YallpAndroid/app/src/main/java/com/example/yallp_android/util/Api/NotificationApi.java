package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationApi {
    @Headers({"Content-Type: application/json"})
    @GET("read")
    Call<Notification[]> getReadNotifications(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @GET("not/read")
    Call<Notification[]> getUnreadNotifications(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST("make/read")
    Call<Notification[]> makeNotificationsRead(
            @Header("Authorization") String token,
            @Body Notification[] readNotifications
    );
}
