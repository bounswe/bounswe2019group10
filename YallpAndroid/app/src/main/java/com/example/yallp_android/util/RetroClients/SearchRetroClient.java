package com.example.yallp_android.util.RetroClients;

import com.example.yallp_android.util.Api.SearchApi;
import com.example.yallp_android.util.Api.WritingApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/search/";
    private Retrofit retrofit = null;
    private static SearchRetroClient instance;

    private SearchRetroClient() {
        if (retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static SearchRetroClient getInstance() {

        if (instance == null) {
            instance = new SearchRetroClient();
        }
        return instance;

    }

    public SearchApi getSearchApi() {
        return retrofit.create(SearchApi.class);
    }

}
