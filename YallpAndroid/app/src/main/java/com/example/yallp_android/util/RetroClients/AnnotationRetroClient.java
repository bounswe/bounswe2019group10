package com.example.yallp_android.util.RetroClients;

import com.example.yallp_android.util.Api.AnnotationApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnnotationRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/";
    private Retrofit retrofit = null;
    private static AnnotationRetroClient instance;

    private AnnotationRetroClient(){
        if(retrofit == null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
    public static AnnotationRetroClient getInstance(){

        if(instance == null){
            instance = new AnnotationRetroClient();
        }
        return instance;

    }
    public AnnotationApi getAnnotationApi(){
        return retrofit.create(AnnotationApi.class);
    }
}
