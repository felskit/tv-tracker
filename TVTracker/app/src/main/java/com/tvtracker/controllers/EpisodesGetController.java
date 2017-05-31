package com.tvtracker.controllers;

import android.content.Context;

import com.tvtracker.interfaces.IEpisodesGetFragment;
import com.tvtracker.models.Episode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class EpisodesGetController implements Callback<Episode> {
    private EpisodesGetAPI mAPI;
    private IEpisodesGetFragment mFragment;
    private Context mContext;

    public EpisodesGetController(IEpisodesGetFragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
        mAPI = retrofit.create(EpisodesGetAPI.class);
    }

    @Override
    public void onResponse(Call<Episode> call, Response<Episode> response) {
        if (response.isSuccessful()) {
            Episode episode = response.body();
            if (episode != null) {
                mFragment.setData(episode);
            }
            else {
                call.clone().enqueue(this);
            }
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