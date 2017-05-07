package com.tvtracker.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.ConstructorConstructor;
import com.tvtracker.interfaces.ISeriesFragment;
import com.tvtracker.models.Show;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jacek on 07.05.2017.
 */

public class SeriesController implements Callback<Show> {
    private ControllerConfig mConfig = new ControllerConfig();
    private SeriesAPI mSeriesAPI;
    private ISeriesFragment mSeriesFragment;

    public SeriesController(ISeriesFragment fragment) {
        mSeriesFragment = fragment;
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mConfig.getBaseApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mSeriesAPI = retrofit.create(SeriesAPI.class);
    }
    @Override
    public void onResponse(Call<Show> call, Response<Show> response) {
        if (response.isSuccessful()) {
            Show show = response.body();
            mSeriesFragment.update(show);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Show> call, Throwable t) {
        t.printStackTrace();
    }

    public void getSeries(int id) {
        Call<Show> call = mSeriesAPI.getSeries(id, ControllerConfig.userId);
        call.enqueue(this);
    }

    private interface SeriesAPI {
        @GET("shows/{id}")
        Call<Show> getSeries(@Path("id") int id, @Query("userId") int userId);
    }
}
