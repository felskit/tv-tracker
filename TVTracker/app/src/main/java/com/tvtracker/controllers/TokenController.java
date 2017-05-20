package com.tvtracker.controllers;

import android.content.Context;

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
    private Context mContext;

    public TokenController(Context context){
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
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
