package com.example.yallp_android.util.RetroClients;

import com.example.yallp_android.util.Api.CommentApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/";
    private Retrofit retrofit = null;
    private static CommentRetroClient instance;

    private CommentRetroClient(){
        if(retrofit == null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
    public static CommentRetroClient getInstance(){

        if(instance == null){
            instance = new CommentRetroClient();
        }
        return instance;

    }
    public CommentApi getCommentApi(){
        return retrofit.create(CommentApi.class);
    }
}
