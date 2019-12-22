package com.example.yallp_android.util.RetroClients;

import com.example.yallp_android.util.Api.UserReportApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserReportRetroClient {
    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/report/";
    private Retrofit retrofit = null;
    private static UserReportRetroClient instance;

    private UserReportRetroClient() {
        if (retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static UserReportRetroClient getInstance() {

        if (instance == null) {
            instance = new UserReportRetroClient();
        }
        return instance;

    }


    public UserReportApi getUserReportApi() {
        return retrofit.create(UserReportApi.class);
    }
}
