package com.example.yallp_android.util.Api;

import com.example.yallp_android.models.ImageUrl;
import com.example.yallp_android.models.LoginUserWithEmail;
import com.example.yallp_android.models.LoginUserWithName;
import com.example.yallp_android.models.MemberLanguage;
import com.example.yallp_android.models.SignUpUser;
import com.example.yallp_android.models.Token;
import com.example.yallp_android.models.UserInfo;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {

    @Headers({"Content-Type: application/json"})
    @POST("register")
    Call<Token> signup(
            @Body SignUpUser signupUser
    );

    @Headers({"Content-Type: application/json"})
    @POST("authenticate")
    Call<Token> loginWithName(
            @Body LoginUserWithName loginUserWithName
    );

    @Headers({"Content-Type: application/json"})
    @POST("authenticate")
    Call<Token> loginWithEmail(
            @Body LoginUserWithEmail loginUserWithEmail
    );

    @Headers({"Content-Type: application/json"})
    @GET("member/profile")
    Call<UserInfo> getProfileInfo(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST("member/addlang")
    Call<MemberLanguage> addNewLanguages(
            @Header("Authorization") String token,
            @Body String[] newLanguages
    );

    @Headers({"Content-Type: application/json"})
    @PUT("member/update")
    Call<Token> updateProfileInfo(
            @Header("Authorization") String token,
            @Body UserInfo newProfileInfo
    );

    @Headers({"Content-Type: application/json"})
    @POST("member/removelang")
    Call<MemberLanguage[]> removeLanguage(
            @Header("Authorization") String token,
            @Body String[] languageToRemove
    );

    @Headers({"Content-Type: application/json"})
    @GET("member/{memberId}")
    Call<UserInfo> getProfileInfoWithId(
            @Header("Authorization") String token,
            @Path("memberId") int memberId
    );

    @Headers({"Content-Type: application/json"})
    @GET("member/username/{username}")
    Call<UserInfo> getProfileInfoWithName(
            @Header("Authorization") String token,
            @Path("username") String username
    );

    @Multipart
    @POST("member/profileImage")
    Call<ImageUrl> profileImage(
            @Header("Authorization") String token,
            @Part MultipartBody.Part file
    );

}
