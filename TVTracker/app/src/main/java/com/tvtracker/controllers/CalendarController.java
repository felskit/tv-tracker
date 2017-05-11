package com.tvtracker.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvtracker.interfaces.ICalendarFragment;
import com.tvtracker.models.CalendarEpisode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jacek on 11.05.2017.
 */

public class CalendarController implements Callback<CalendarEpisode[]> {
    private ControllerConfig mConfig = new ControllerConfig();
    private CalendarAPI mAPI;
    private ICalendarFragment mFragment;

    public CalendarController(ICalendarFragment fragment) {
        mFragment = fragment;
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mConfig.getBaseApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mAPI = retrofit.create(CalendarAPI.class);
    }

    @Override
    public void onResponse(Call<CalendarEpisode[]> call, Response<CalendarEpisode[]> response) {
        if (response.isSuccessful()) {
            CalendarEpisode[] episodes = response.body();
            mFragment.addEpisodes(episodes);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<CalendarEpisode[]> call, Throwable t) {
        t.printStackTrace();
    }

    public void getEpisodes(int month, int year) {
        Call<CalendarEpisode[]> call = mAPI.getEpisodes(month, year, ControllerConfig.userId);
        call.enqueue(this);
    }

    private interface CalendarAPI {
        @GET("calendar")
        Call<CalendarEpisode[]> getEpisodes(@Query("month") int month, @Query("year") int year, @Query("userId") int userId);
    }
}
