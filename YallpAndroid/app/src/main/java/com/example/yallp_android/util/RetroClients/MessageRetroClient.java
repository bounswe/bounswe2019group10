package com.example.yallp_android.util.RetroClients;

import com.example.yallp_android.util.Api.LanguageApi;
import com.example.yallp_android.util.Api.MessageApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/";
    private Retrofit retrofit = null;
    private static MessageRetroClient instance;

    private MessageRetroClient(){
        if(retrofit == null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
    public static MessageRetroClient getInstance(){

        if(instance == null){
            instance = new MessageRetroClient();
        }
        return instance;

    }
    public MessageApi getMessageApi(){
        return retrofit.create(MessageApi.class);
    }
}
