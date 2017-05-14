package com.tvtracker.controllers;

import android.support.v7.app.AlertDialog;

import com.tvtracker.LoginActivity;
import com.tvtracker.R;
import com.tvtracker.interfaces.ILoginActivity;
import com.tvtracker.models.User;
import com.tvtracker.models.UserId;

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
    private ControllerConfig mConfig = new ControllerConfig();
    private LoginAPI mAPI;
    private ILoginActivity mActivity;
    private AlertDialog dialog;
    private UserId userId;

    public LoginController(LoginActivity loginActivity) {
        mActivity = loginActivity;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(loginActivity);
        alertDialogBuilder.setTitle(loginActivity.getString(R.string.login_dialog_title));
        alertDialogBuilder
                .setMessage(loginActivity.getString(R.string.login_dialog_message))
                .setCancelable(false);
        dialog = alertDialogBuilder.create();
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mConfig.getBaseApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mAPI = retrofit.create(LoginAPI.class);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
            dialog.hide();
            User user = response.body();
            mActivity.redirect(user.id);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        if(mActivity.isVisible()) {
            dialog.show();
        }
        login(userId);
        t.printStackTrace();
    }

    public void login(UserId userId) {
        this.userId = userId;
        Call<User> call = mAPI.login(userId);
        call.enqueue(this);
    }

    private interface LoginAPI {
        @POST("login")
        Call<User> login(@Body UserId userId);
    }
}