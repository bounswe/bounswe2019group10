package com.example.yallp_android.util.Api;


import com.example.yallp_android.models.Annotation;
import com.example.yallp_android.models.AnnotationDTO;
import com.example.yallp_android.models.AnnotationDeleteDTO;
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
import retrofit2.http.Query;

public interface AnnotationApi {


    @Headers({"Content-Type: application/json"})
    @POST("annotation/create")
    Call<AnnotationDTO> createAnnotation(@Header("Authorization") String token, @Body AnnotationDTO annotationDTO);

    @Headers({"Content-Type: application/json"})
    @POST("annotation/update")
    Call<AnnotationDTO> updateAnnotation(@Header("Authorization") String token, @Body AnnotationDTO annotationDTO);

    @Headers({"Content-Type: application/json"})
    @POST("annotation/delete")
    Call<AnnotationDeleteDTO> deleteAnnotation(@Header("Authorization") String token, @Query("id") int id);

    @Headers({"Content-Type: application/json"})
    @GET("annotation/all/{writingId}")
    Call<Annotation[]> getAllAnnotationsOfWriting(@Header("Authorization") String token, @Path("writingId") int writingId);

    @Headers({"Content-Type: application/json"})
    @GET("annotation/{id}")
    Call<Annotation> getAnnotationById(@Header("Authorization") String token, @Path("writingId") int writingId);

}
