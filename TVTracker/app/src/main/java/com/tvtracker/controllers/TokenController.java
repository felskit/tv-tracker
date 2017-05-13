package com.tvtracker.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvtracker.models.DeviceToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Jacek on 13.05.2017.
 */

public class TokenController implements Callback<String> {
    private ControllerConfig mConfig = new ControllerConfig();
    private TokenAPI mAPI;

    public TokenController(){}

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mConfig.getBaseApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mAPI = retrofit.create(TokenAPI.class);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (!response.isSuccessful()) {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        t.printStackTrace();
    }

    public void updateToken(String oldToken, String newToken) {
        if(ControllerConfig.userId != -1) {
            Call<String> call = mAPI.updateToken(new DeviceToken(ControllerConfig.userId, oldToken, newToken));
            call.enqueue(this);
        }
    }

    private interface TokenAPI {
        @POST("login/updateToken")
        Call<String> updateToken(@Body DeviceToken watchedEpisode);
    }
}
