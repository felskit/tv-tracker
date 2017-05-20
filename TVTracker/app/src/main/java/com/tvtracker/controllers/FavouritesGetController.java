package com.tvtracker.controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvtracker.interfaces.IFavouritesGetFragment;
import com.tvtracker.models.ListShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class FavouritesGetController implements Callback<ListShow[]> {
    private ControllerConfig mConfig = new ControllerConfig();
    private FavouritesGetAPI mAPI;
    private IFavouritesGetFragment mFragment;
    private Context mContext;

    public FavouritesGetController(IFavouritesGetFragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
        mAPI = retrofit.create(FavouritesGetAPI.class);
    }

    @Override
    public void onResponse(Call<ListShow[]> call, Response<ListShow[]> response) {
        if (response.isSuccessful()) {
            ListShow[] shows = response.body();
            mFragment.updateList(shows);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ListShow[]> call, Throwable t) {
        t.printStackTrace();
    }

    public void getFavourites() {
        Call<ListShow[]> call = mAPI.getFavourites(ControllerConfig.userId);
        call.enqueue(this);
    }

    public void getSuggested() {
        Call<ListShow[]> call = mAPI.getSuggested(ControllerConfig.userId);
        call.enqueue(this);
    }

    private interface FavouritesGetAPI {
        @GET("favourites")
        Call<ListShow[]> getFavourites(@Query("userId") int id);

        @GET("suggested")
        Call<ListShow[]> getSuggested(@Query("userId") int id);
    }
}
