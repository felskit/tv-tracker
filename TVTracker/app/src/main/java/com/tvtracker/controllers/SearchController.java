package com.tvtracker.controllers;

import android.content.Context;

import com.tvtracker.interfaces.ISearchFragment;
import com.tvtracker.models.ListShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class SearchController implements Callback<ListShow[]> {
    private SearchAPI mAPI;
    private ISearchFragment mFragment;
    private Context mContext;

    public SearchController(ISearchFragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public void start() {
        Retrofit retrofit = ControllerConfig.getRetrofit(mContext);
        mAPI = retrofit.create(SearchAPI.class);
    }
    @Override
    public void onResponse(Call<ListShow[]> call, Response<ListShow[]> response) {
        if (response.isSuccessful()) {
            ListShow[] shows = response.body();
            mFragment.updateSuggestions(shows);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ListShow[]> call, Throwable t) {
        t.printStackTrace();
    }

    public void search(String text) {
        Call<ListShow[]> call = mAPI.search(text);
        call.enqueue(this);
    }

    private interface SearchAPI {
        @GET("shows/list/{text}")
        Call<ListShow[]> search(@Path("text") String text);
    }
}
