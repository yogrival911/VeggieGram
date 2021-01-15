package com.veggiegram.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    public static Retrofit INSTANCE = null;
    public static Retrofit getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Retrofit.Builder()
                    .baseUrl("https://api.veggiegram.in/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INSTANCE;
    }
}
