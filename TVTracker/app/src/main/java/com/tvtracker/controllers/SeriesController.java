package com.tvtracker.controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class SeriesController implements Callback<Show> {
    private ControllerConfig mConfig = new ControllerConfig();
    private SeriesAPI mAPI;
    private ISeriesFragment mFragment;
    private Context mContext;

    public SeriesController(ISeriesFragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
        mAPI = retrofit.create(SeriesAPI.class);
    }
    @Override
    public void onResponse(Call<Show> call, Response<Show> response) {
        if (response.isSuccessful()) {
            Show show = response.body();
            mFragment.update(show);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Show> call, Throwable t) {
        t.printStackTrace();
    }

    public void getSeries(int id) {
        Call<Show> call = mAPI.getSeries(id, ControllerConfig.userId);
        call.enqueue(this);
    }

    private interface SeriesAPI {
        @GET("shows/{id}")
        Call<Show> getSeries(@Path("id") int id, @Query("userId") int userId);
    }
}
