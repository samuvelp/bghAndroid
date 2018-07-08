package com.gospel.bethany.bgh.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.activities.eventcalendar.EventCalendarActivity;
import com.gospel.bethany.bgh.activities.main.MainActivity;
import com.gospel.bethany.bgh.activities.tap.TapActivity;

public class FirebaseNotification extends FirebaseMessagingService {
    private static String TAG = "FirebaseMessageSerive";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String notificationTitle = null, notificationBody = null, notificationType = null;

        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().get("body"));
            notificationTitle = remoteMessage.getData().get("title");
            notificationBody = remoteMessage.getData().get("body");
            notificationType = remoteMessage.getData().get("type");
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        sendNotification(notificationTitle, notificationBody, notificationType);
    }


    private void sendNotification(String notificationTitle, String notificationBody, String notificationType) {
        Intent intent = null;
        if (notificationType.equals("tap")) {
            intent = new Intent(this, TapActivity.class);
        } else if (notificationType.equals("calendar")) {
            intent = new Intent(this, EventCalendarActivity.class);
        } else{
            intent = new Intent(this, MainActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setAutoCancel(true)   //Automatically delete the notification
                .setSmallIcon(R.mipmap.ic_notify) //Notification icon
                .setContentIntent(pendingIntent)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setSound(defaultSoundUri);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}
