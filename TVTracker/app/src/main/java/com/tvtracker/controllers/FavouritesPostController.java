package com.tvtracker.controllers;

import android.content.Context;

import com.tvtracker.interfaces.IFavouritesPostFragment;
import com.tvtracker.models.Favourites;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class FavouritesPostController implements Callback<String> {
    private FavouritesPostAPI mAPI;
    private IFavouritesPostFragment mFragment;
    private Context mContext;
    private boolean undo = false;

    public FavouritesPostController(IFavouritesPostFragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
        mAPI = retrofit.create(FavouritesPostAPI.class);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            String message = response.body();
            if (message != null) {
                mFragment.notify(message, undo);
            }
            else {
                call.clone().enqueue(this);
            }
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        t.printStackTrace();
    }

    public void addFavourite(int showId) {
        Call<String> call = mAPI.addFavourite(new Favourites(ControllerConfig.userId, showId));
        call.enqueue(this);
        undo = false;
    }

    public void removeFavourite(int showId) {
        Call<String> call = mAPI.removeFavourite(new Favourites(ControllerConfig.userId, showId));
        call.enqueue(this);
        undo = true;
    }

    private interface FavouritesPostAPI {
        @POST("favourites/add")
        Call<String> addFavourite(@Body Favourites favourites);

        @POST("favourites/remove")
        Call<String> removeFavourite(@Body Favourites favourites);
    }
}
