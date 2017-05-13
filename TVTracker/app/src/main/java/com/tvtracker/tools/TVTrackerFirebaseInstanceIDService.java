package com.tvtracker.tools;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tvtracker.controllers.TokenController;

/**
 * Created by Jacek on 13.05.2017.
 */

public class TVTrackerFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static String currentToken = null;
    private TokenController controller = null;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        if(controller == null) {
            controller = new TokenController();
            controller.start();
            currentToken = FirebaseInstanceId.getInstance().getToken();
        }

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        controller.updateToken(currentToken, refreshedToken);

        currentToken = refreshedToken;
    }

    public static String getToken() {
        return currentToken;
    }
}
