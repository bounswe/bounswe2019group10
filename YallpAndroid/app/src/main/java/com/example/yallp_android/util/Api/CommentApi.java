package com.example.yallp_android.util.Api;


import com.example.yallp_android.models.Comment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface CommentApi {


    @Headers({"Content-Type: application/json"})
    @GET("comment")
    Call<Comment[]> getComments(@Header("Authorization") String token);


    @Headers({"Content-Type: application/json"})
    @GET("comment/{memberId}")
    Call<Comment[]> getCommentsbyId(@Header("Authorization") String token,@Path("memberId") int memberId);
}
