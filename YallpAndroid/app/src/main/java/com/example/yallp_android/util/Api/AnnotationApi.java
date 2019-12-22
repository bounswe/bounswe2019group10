package com.example.yallp_android.util.Api;


import com.example.yallp_android.models.Annotation;
import com.example.yallp_android.models.Conversation;
import com.example.yallp_android.models.Message;
import com.example.yallp_android.models.SendMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnnotationApi {


    @Headers({"Content-Type: application/json"})
    @POST("annotation/create")
    Call<Annotation> createAnnotation(@Header("Authorization") String token, @Body Annotation annotation);

    @Headers({"Content-Type: application/json"})
    @POST("annotation/update")
    Call<Annotation> updateAnnotation(@Header("Authorization") String token, @Body Annotation annotation);

    @Headers({"Content-Type: application/json"})
    @GET("annotation/all/{writingId}")
    Call<Annotation[]> getAllAnnotationsOfWriting(@Header("Authorization") String token, @Path("writingId") int writingId);

    @Headers({"Content-Type: application/json"})
    @GET("annotation/{id}")
    Call<Annotation> getAnnotationById(@Header("Authorization") String token, @Path("writingId") int writingId);

}
