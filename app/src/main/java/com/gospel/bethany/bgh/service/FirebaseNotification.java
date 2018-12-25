package com.gospel.bethany.bgh.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.activities.eventcalendar.EventCalendarActivity;
import com.gospel.bethany.bgh.activities.main.MainActivity;
import com.gospel.bethany.bgh.activities.sermon.SermonActivity;
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
            sendNotification(notificationTitle, notificationBody, notificationType);
        }
    }


    private void sendNotification(String notificationTitle, String notificationBody, String notificationType) {
        Intent intent = null;
        String CHANNEL_ID = "01";
        if (notificationType.equals("tap")) {
            intent = new Intent(this, TapActivity.class);
        } else if (notificationType.equals("calendar")) {
            intent = new Intent(this, EventCalendarActivity.class);
        } else if (notificationType.equals("sermon")) {
            intent = new Intent(this,SermonActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_notify)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationBody))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

        {
            CharSequence name = notificationTitle;
            String description = notificationBody;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("01", name, importance);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }

}
