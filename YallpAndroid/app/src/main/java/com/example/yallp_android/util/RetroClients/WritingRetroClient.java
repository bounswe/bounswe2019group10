package com.example.yallp_android.util.RetroClients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.yallp_android.util.Api.WritingApi;

public class WritingRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/writing/";
    private Retrofit retrofit = null;
    private static WritingRetroClient instance;

    private WritingRetroClient() {
        if (retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static WritingRetroClient getInstance() {

        if (instance == null) {
            instance = new WritingRetroClient();
        }
        return instance;

    }

    public WritingApi getWritingApi() {
        return retrofit.create(WritingApi.class);
    }


}
