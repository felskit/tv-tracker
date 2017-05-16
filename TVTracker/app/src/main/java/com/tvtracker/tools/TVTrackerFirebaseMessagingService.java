package com.tvtracker.tools;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tvtracker.MainActivity;
import com.tvtracker.R;

import java.util.Map;


public class TVTrackerFirebaseMessagingService extends FirebaseMessagingService {

    private static int id = 0;


    @Override
    public void onMessageReceived(RemoteMessage message) {
            super.onMessageReceived(message);
            RemoteMessage.Notification notification = message.getNotification();
            Map<String, String> data = message.getData();
            sendNotification(notification.getTitle(), notification.getBody(), data.get("episodeId"));
    }

    private void sendNotification(String messageTitle, String messageBody, String episodeId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("episodeId", episodeId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id++, notificationBuilder.build());
    }
}
