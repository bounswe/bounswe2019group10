package com.example.yallp_android.util.RetroClients;

import com.example.yallp_android.util.Api.LanguageApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LanguageRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/";
    private Retrofit retrofit = null;
    private static LanguageRetroClient instance;

    private LanguageRetroClient(){
        if(retrofit == null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
    public static LanguageRetroClient getInstance(){

        if(instance == null){
            instance = new LanguageRetroClient();
        }
        return instance;

    }
    public LanguageApi getLanguageApi(){
        return retrofit.create(LanguageApi.class);
    }
}
