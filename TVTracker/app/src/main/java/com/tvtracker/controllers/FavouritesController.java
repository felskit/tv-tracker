package com.tvtracker.controllers;

import android.content.Context;

import com.tvtracker.interfaces.IFavouritesGetFragment;
import com.tvtracker.models.ListShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class FavouritesController implements Callback<ListShow[]> {
    private FavouritesGetAPI mAPI;
    private IFavouritesGetFragment mFragment;
    private Context mContext;

    public FavouritesController(IFavouritesGetFragment fragment, Context context) {
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
        @POST("favourites")
        Call<ListShow[]> getFavourites(@Body int id);

        @POST("suggested")
        Call<ListShow[]> getSuggested(@Body int id);
    }
}
