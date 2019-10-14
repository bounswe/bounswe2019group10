package com.example.yallp_android.util.RetroClients;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import  com.example.yallp_android.util.Api.UserApi;

public class UserRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/";
    private Retrofit retrofit = null;
    private static UserRetroClient instance;

    private UserRetroClient(){
        if(retrofit == null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static UserRetroClient getInstance(){

        if(instance == null){
            instance = new UserRetroClient();
        }
        return instance;

    }

    public UserApi getUserApi(){
        return retrofit.create(UserApi.class);
    }


}
