package com.example.android.bakingapp.network;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL =
            "http://d17h27t6h515a5.cloudfront.net/topher/" +
                    "2017/May/59121517_baking/";

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null){
             retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();


        }
        return retrofit;
    }
}
