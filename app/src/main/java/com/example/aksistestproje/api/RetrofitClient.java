package com.example.aksistestproje.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;

    private ApiService myApi;


    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(ApiService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null)
            instance = new RetrofitClient();
        return instance;
    }

    public ApiService getMyApi() {
        return myApi;
    }
}
