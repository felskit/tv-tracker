package com.tvtracker.controllers;

import com.tvtracker.interfaces.IEpisodesGetFragment;
import com.tvtracker.models.Episode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class EpisodesGetController implements Callback<Episode> {
    private ControllerConfig mConfig = new ControllerConfig();
    private EpisodesGetAPI mAPI;
    private IEpisodesGetFragment mFragment;

    public EpisodesGetController(IEpisodesGetFragment fragment) {
        mFragment = fragment;
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mConfig.getBaseApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mAPI = retrofit.create(EpisodesGetAPI.class);
    }

    @Override
    public void onResponse(Call<Episode> call, Response<Episode> response) {
        if (response.isSuccessful()) {
            Episode episode = response.body();
            mFragment.setData(episode);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Episode> call, Throwable t) {
        t.printStackTrace();
    }

    public void getEpisode(int id) {
        Call<Episode> call = mAPI.getEpisode(id);
        call.enqueue(this);
    }

    private interface EpisodesGetAPI {
        @GET("episodes/{id}")
        Call<Episode> getEpisode(@Path("id") int id);
    }
}