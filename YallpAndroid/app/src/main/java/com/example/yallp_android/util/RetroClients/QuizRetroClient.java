package com.example.yallp_android.util.RetroClients;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.yallp_android.util.Api.QuizApi;

public class QuizRetroClient {

    private final String baseUrl = "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/";
    private Retrofit retrofit = null;
    private static QuizRetroClient instance;

    private QuizRetroClient(){
        if(retrofit == null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static QuizRetroClient getInstance(){

        if(instance == null){
            instance = new QuizRetroClient();
        }
        return instance;

    }

    public QuizApi getQuizApi(){
        return retrofit.create(QuizApi.class);
    }


}