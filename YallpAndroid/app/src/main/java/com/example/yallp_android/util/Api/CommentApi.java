package com.example.yallp_android.util.Api;


import com.example.yallp_android.models.Comment;
import com.example.yallp_android.models.CommentSubmit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CommentApi {


    @Headers({"Content-Type: application/json"})
    @GET("comment")
    Call<Comment[]> getComments(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST("comment/make")
    Call<Comment> makeComment(
            @Header("Authorization") String token,
            @Body CommentSubmit comment
    );

}
