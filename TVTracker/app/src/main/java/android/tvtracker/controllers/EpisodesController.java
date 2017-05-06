package android.tvtracker.controllers;

import android.tvtracker.models.Episode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class EpisodesController implements Callback<Episode> {
    private ControllerConfig mConfig = new ControllerConfig();
    private EpisodesAPI mEpisodesAPI;

    public EpisodesController() {
        // interfejs implementowany przez fragment
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mConfig.getBaseApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mEpisodesAPI = retrofit.create(EpisodesAPI.class);
    }

    @Override
    public void onResponse(Call<Episode> call, Response<Episode> response) {
        if (response.isSuccessful()) {
            Episode episode = response.body();
            // cos z tym zrobic przez interfejs
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Episode> call, Throwable t) {
        t.printStackTrace();
    }

    public void getEpisode(int id) {
        Call<Episode> call = mEpisodesAPI.getEpisode(id);
        call.enqueue(this);
    }

    private interface EpisodesAPI {
        @GET("episodes/{id}")
        Call<Episode> getEpisode(@Path("id") int id);
    }
}