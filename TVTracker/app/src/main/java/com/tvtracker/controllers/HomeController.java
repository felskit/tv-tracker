package com.tvtracker.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvtracker.interfaces.IHomeFragment;
import com.tvtracker.models.HomeEpisode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class HomeController implements Callback<HomeEpisode[]> {
    private ControllerConfig mConfig = new ControllerConfig();
    private HomeAPI mAPI;
    private IHomeFragment mFragment;

    public HomeController(IHomeFragment fragment) {
        mFragment = fragment;
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mConfig.getBaseApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
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
        @GET("home")
        Call<HomeEpisode[]> getEpisodes(@Query("userId") int id);
    }
}
