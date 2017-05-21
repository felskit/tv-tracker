package com.tvtracker.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.tvtracker.R;

public class NetworkStateReceiver extends BroadcastReceiver {
    private AlertDialog mDialog;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mNi;

    public NetworkStateReceiver(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getString(R.string.network_dialog_title));
        alertDialogBuilder
                .setMessage(context.getString(R.string.network_dialog_message))
                .setCancelable(false);
        mDialog = alertDialogBuilder.create();

        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNi = mConnectivityManager.getActiveNetworkInfo();
    }

    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            mDialog.hide();
        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            mDialog.show();
        }
    }

    public boolean isConnected() {
        return mConnectivityManager.getActiveNetworkInfo() != null;
    }
}