package com.tvtracker.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.tvtracker.R;

/**
 * Created by Jacek on 08.05.2017.
 */

public class NetworkStateReceiver extends BroadcastReceiver {
    private AlertDialog dialog;
    private ConnectivityManager manager;
    private NetworkInfo ni;

    public NetworkStateReceiver(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getString(R.string.network_dialog_title));
        alertDialogBuilder
                .setMessage(context.getString(R.string.network_dialog_message))
                .setCancelable(false);
        dialog = alertDialogBuilder.create();

        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = manager.getActiveNetworkInfo();
    }

    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if(ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            dialog.hide();
        }
        else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            dialog.show();
        }
    }

    public boolean isConnected()
    {
        return manager.getActiveNetworkInfo() != null;
    }
}