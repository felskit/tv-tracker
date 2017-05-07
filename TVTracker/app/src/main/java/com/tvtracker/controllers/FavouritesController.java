package com.tvtracker.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tvtracker.interfaces.IFavouritesFragment;
import com.tvtracker.models.ListShow;

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

public class FavouritesController implements Callback<ListShow[]> {
    private ControllerConfig mConfig = new ControllerConfig();
    private FavouritesAPI mFavouritesAPI;
    private IFavouritesFragment mFavouritesFragment;

    public FavouritesController(IFavouritesFragment fragment) {
        mFavouritesFragment = fragment;
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mConfig.getBaseApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mFavouritesAPI = retrofit.create(FavouritesAPI.class);
    }

    @Override
    public void onResponse(Call<ListShow[]> call, Response<ListShow[]> response) {
        if (response.isSuccessful()) {
            ListShow[] shows = response.body();
            mFavouritesFragment.updateList(shows);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ListShow[]> call, Throwable t) {
        t.printStackTrace();
    }

    public void getFavourites(int id) {
        Call<ListShow[]> call = mFavouritesAPI.getFavourites(id);
        call.enqueue(this);
    }

    public void getSuggested(int id) {
        Call<ListShow[]> call = mFavouritesAPI.getSuggested(id);
        call.enqueue(this);
    }

    private interface FavouritesAPI {
        @GET("favourites")
        Call<ListShow[]> getFavourites(@Query("userId") int id);

        @GET("suggested")
        Call<ListShow[]> getSuggested(@Query("userId") int id);
    }
}
