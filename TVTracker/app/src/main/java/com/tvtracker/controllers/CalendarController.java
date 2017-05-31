package com.tvtracker.controllers;

import android.content.Context;

import com.tvtracker.interfaces.ICalendarFragment;
import com.tvtracker.models.CalendarData;
import com.tvtracker.models.CalendarEpisode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class CalendarController implements Callback<CalendarEpisode[]> {
    private CalendarAPI mAPI;
    private ICalendarFragment mFragment;
    private Context mContext;

    public CalendarController(ICalendarFragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
        mAPI = retrofit.create(CalendarAPI.class);
    }

    @Override
    public void onResponse(Call<CalendarEpisode[]> call, Response<CalendarEpisode[]> response) {
        if (response.isSuccessful()) {
            CalendarEpisode[] episodes = response.body();
            if (episodes != null) {
                mFragment.addEpisodes(episodes);
            }
            else {
                call.clone().enqueue(this);
            }
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<CalendarEpisode[]> call, Throwable t) {
        t.printStackTrace();
    }

    public void getEpisodes(int month, int year) {
        Call<CalendarEpisode[]> call = mAPI.getEpisodes(new CalendarData(ControllerConfig.userId, month, year));
        call.enqueue(this);
    }

    private interface CalendarAPI {
        @POST("calendar")
        Call<CalendarEpisode[]> getEpisodes(@Body CalendarData data);
    }
}
