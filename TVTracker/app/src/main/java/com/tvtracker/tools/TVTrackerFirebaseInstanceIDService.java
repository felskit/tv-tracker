package com.tvtracker.tools;

import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tvtracker.controllers.TokenController;

/**
 * Created by Jacek on 13.05.2017.
 */

public class TVTrackerFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static String currentToken = null;
    private static String oldToken = null;
    private static TokenController controller = null;
    private static boolean tokenChanged = false;


    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        oldToken = getTokenFromPrefs();
        currentToken = refreshedToken;
        tokenChanged = true;
        saveTokenToPrefs(currentToken);
    }

    public static void updateToken() {
        if(tokenChanged) {
            if(controller == null) {
                controller = new TokenController();
                controller.start();
            }
            controller.updateToken(oldToken, currentToken);
            tokenChanged = false;
        }
    }

    public static String getToken() {
        return currentToken;
    }

    private void saveTokenToPrefs(String _token)
    {
        // Access Shared Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // Save to SharedPreferences
        editor.putString("registration_id", _token);
        editor.apply();
    }

    private String getTokenFromPrefs()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }
}
