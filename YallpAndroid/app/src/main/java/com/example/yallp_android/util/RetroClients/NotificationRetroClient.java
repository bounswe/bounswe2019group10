package com.example.yallp_android.util.RetroClients;

import com.example.yallp_android.util.Api.NotificationApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/notification/";
    private Retrofit retrofit = null;
    private static NotificationRetroClient instance;

    private NotificationRetroClient(){
        if(retrofit == null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
    public static NotificationRetroClient getInstance(){

        if(instance == null){
            instance = new NotificationRetroClient();
        }
        return instance;

    }
    public NotificationApi getNotificationApi(){
        return retrofit.create(NotificationApi.class);
    }
}
