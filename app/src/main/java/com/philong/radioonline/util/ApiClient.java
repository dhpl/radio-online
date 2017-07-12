package com.philong.radioonline.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Long on 6/30/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "http://api.dirble.com/v2/";
    public static final String API_KEY = "c9a1b95383c2604d17f41bbba9";
    public static Retrofit mRetrofit = null;

    public static Retrofit getApiClient(){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

}
