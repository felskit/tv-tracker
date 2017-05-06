package android.tvtracker.controllers;

import android.tvtracker.interfaces.ILoginActivity;
import android.tvtracker.models.User;
import android.tvtracker.models.UserId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class LoginController implements Callback<User> {
    private static final String BASE_URL = "http://192.168.1.2/tvtracker/api/";
    private LoginAPI mLoginAPI;
    private ILoginActivity mLoginActivity;

    public LoginController(ILoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mLoginAPI = retrofit.create(LoginAPI.class);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
            User user = response.body();
            mLoginActivity.redirect(user.id);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }

    public void login(UserId userId) {
        Call<User> call = mLoginAPI.login(userId);
        call.enqueue(this);
    }

    private interface LoginAPI {
        @POST("login")
        Call<User> login(@Body UserId userId);
    }
}