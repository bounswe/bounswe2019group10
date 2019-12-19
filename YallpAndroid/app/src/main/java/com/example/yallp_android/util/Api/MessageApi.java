package com.example.yallp_android.util.Api;


import com.example.yallp_android.models.Comment;
import com.example.yallp_android.models.CommentSubmit;
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

public interface MessageApi {


    @Headers({"Content-Type: application/json"})
    @GET("message/{conversationId}")
    Call<Conversation> getConversation(@Header("Authorization") String token,@Path("conversationId") int conversationId);

    @Headers({"Content-Type: application/json"})
    @GET("message/conversations")
    Call<Conversation[]> getAllConversations(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST("message/send")
    Call<Message> sendMessage(
            @Header("Authorization") String token,
            @Body SendMessage sendMessage
    );

}
