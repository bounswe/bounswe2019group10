package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.Comment;
import com.example.yallp_android.models.CommentSubmit;
import com.example.yallp_android.models.Rating;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentApi {

    @Headers({"Content-Type: application/json"})
    @GET("comment")
    Call<Comment[]> getComments(@Header("Authorization") String token);


    @Headers({"Content-Type: application/json"})
    @GET("comment/{memberId}")
    Call<Comment[]> getCommentsbyId(@Header("Authorization") String token,@Path("memberId") int memberId);

    @Headers({"Content-Type: application/json"})
    @POST("comment/make")
    Call<Comment> makeComment(
            @Header("Authorization") String token,
            @Body CommentSubmit comment
    );

    @Headers({"Content-Type: application/json"})
    @GET("comment/rating")
    Call<Rating> getRating(@Header("Authorization") String token);


    @Headers({"Content-Type: application/json"})
    @GET("comment/rating/{memberId}")
    Call<Rating> getRatingByMemberId(@Header("Authorization") String token,@Path("memberId") int memberId);
}
