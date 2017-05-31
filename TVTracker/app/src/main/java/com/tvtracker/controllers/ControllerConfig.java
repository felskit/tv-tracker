package com.tvtracker.controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvtracker.tools.ConfiguredHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerConfig {
    private static final String baseApiUrl = "https:/192.168.1.2/tvtracker/api/";
    public static int userId = -1;

    public static Retrofit getRetrofit(Context context) {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder().client(ConfiguredHttpClient.getHttpClient(context))
                .baseUrl(baseApiUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static String getBaseApiUrl() {
        return baseApiUrl;
    }
}
