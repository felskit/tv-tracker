package com.tvtracker.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.util.Objects;

public class ImageDownloader {
    private static WifiManager mWifiManager;
    private static SharedPreferences mPrefs;

    public static void init(WifiManager wifiManager, SharedPreferences prefs) {
        mWifiManager = wifiManager;
        mPrefs = prefs;
    }

    public static void execute(Context context, String url, Boolean recycle, ImageView imageView) {
        String pref = mPrefs.getString("pref_images", "-1");
        if (Objects.equals(pref, "2") || Objects.equals(pref, "1") && !mWifiManager.isWifiEnabled() || url == null) {
            return;
        }

        try {
            RequestCreator rc = Picasso.with(context).load(url);
            if (recycle) {
                rc = rc.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
            }
            rc.into(imageView);
        }
        catch (NullPointerException ex) {
            Log.e("MAIN", "Picasso threw NullPointerException, possible problems with network connection", ex);
        }
    }

    public static void execute(Context context, String url, Boolean recycle, Transformation transformation, ImageView imageView) {
        String pref = mPrefs.getString("pref_images", "-1");
        if (Objects.equals(pref, "2") || Objects.equals(pref, "1") && !mWifiManager.isWifiEnabled() || url == null) {
            return;
        }

        try {
            RequestCreator rc = Picasso.with(context).load(url);
            if (recycle) {
                rc = rc.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
            }
            rc.transform(transformation).into(imageView);
        }
        catch (NullPointerException ex) {
            Log.e("MAIN", "Picasso threw NullPointerException, possible problems with network connection", ex);
        }
    }
}