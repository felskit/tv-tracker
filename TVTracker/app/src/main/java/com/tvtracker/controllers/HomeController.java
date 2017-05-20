package com.tvtracker.controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvtracker.interfaces.IHomeFragment;
import com.tvtracker.models.HomeEpisode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class HomeController implements Callback<HomeEpisode[]> {
    private ControllerConfig mConfig = new ControllerConfig();
    private HomeAPI mAPI;
    private IHomeFragment mFragment;
    private Context mContext;

    public HomeController(IHomeFragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
        mAPI = retrofit.create(HomeAPI.class);
    }

    @Override
    public void onResponse(Call<HomeEpisode[]> call, Response<HomeEpisode[]> response) {
        if (response.isSuccessful()) {
            HomeEpisode[] episodes = response.body();
            mFragment.updateEpisodes(episodes);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<HomeEpisode[]> call, Throwable t) {
        t.printStackTrace();
    }

    public void getEpisodes() {
        Call<HomeEpisode[]> call = mAPI.getEpisodes(ControllerConfig.userId);
        call.enqueue(this);
    }

    private interface HomeAPI {
        @POST("home")
        Call<HomeEpisode[]> getEpisodes(@Body int id);
    }
}
