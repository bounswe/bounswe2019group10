package com.example.yallp_android.util.Api;


import com.example.yallp_android.models.Annotation;
import com.example.yallp_android.models.ImageAnnotationDTO;
import com.example.yallp_android.models.AnnotationDTO;
import com.example.yallp_android.models.TextAnnotationDeleteDTO;

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
    Call<AnnotationDTO> createTextAnnotation(@Header("Authorization") String token, @Body AnnotationDTO annotationDTO);

    @Headers({"Content-Type: application/json"})
    @POST("annotation/update")
    Call<AnnotationDTO> updateTextAnnotation(@Header("Authorization") String token, @Body AnnotationDTO annotationDTO);

    @Headers({"Content-Type: application/json"})
    @POST("annotation/delete")
    Call<TextAnnotationDeleteDTO> deleteTextAnnotation(@Header("Authorization") String token, @Query("id") int id);

    @Headers({"Content-Type: application/json"})
    @GET("annotation/all/{writingId}")
    Call<Annotation[]> getAllTextAnnotationsOfWriting(@Header("Authorization") String token, @Path("writingId") int writingId);

    @Headers({"Content-Type: application/json"})
    @POST("annotation/image/create")
    Call<AnnotationDTO> createImageAnnotation(@Header("Authorization") String token, @Body ImageAnnotationDTO imageAnnotationDTO);

    @Headers({"Content-Type: application/json"})
    @GET("annotation/image/all")
    Call<Annotation[]> getAllImageAnnotationsOfWriting(@Header("Authorization") String token, @Query("url") String url);
}
