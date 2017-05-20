package com.tvtracker.controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvtracker.interfaces.IEpisodesPostFragment;
import com.tvtracker.interfaces.IPostFragment;
import com.tvtracker.models.WatchedEpisode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class EpisodesPostController implements Callback<String> {
    private ControllerConfig mConfig = new ControllerConfig();
    private EpisodesPostAPI mAPI;
    private IEpisodesPostFragment mFragment;
    private int position = 0;
    private Context mContext;

    public EpisodesPostController(IEpisodesPostFragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
        mAPI = retrofit.create(EpisodesPostAPI.class);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            String message = response.body();
            mFragment.notify(message, position);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        t.printStackTrace();
    }

    public void setWatched(int episodeId, Boolean watched, int position) {
        this.position = position;
        Call<String> call = mAPI.setWatched(new WatchedEpisode(ControllerConfig.userId, episodeId, watched));
        call.enqueue(this);
    }

    private interface EpisodesPostAPI {
        @POST("episodes/watched")
        Call<String> setWatched(@Body WatchedEpisode watchedEpisode);
    }
}