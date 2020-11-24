package com.akar.aakar.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;

import com.akar.aakar.R;
import com.akar.aakar.TransactionSuccessfulActivity;
import com.akar.aakar.activitySplash;
import com.akar.aakar.display.ShowOrdersSR;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    private static final String TAG = "MyFirebaseMsgService";
    String notificationType = "", id = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("text"));

            if (remoteMessage.getData().get("text") != null) {
                notificationType = remoteMessage.getData().get("text");
            }
            if (remoteMessage.getData().get("id") != null) {
                id = remoteMessage.getData().get("id");

                prefs = this.getSharedPreferences("NurseApp", MODE_PRIVATE);
                prefsEditor = prefs.edit();
                prefsEditor.putString("pay_price", id);
                prefsEditor.apply();
                TransactionSuccessfulActivity.get_price = id;
            }

            if (true) {
                scheduleJob();
            } else {
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotificationSr(remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void scheduleJob() {

        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();

    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        prefs = this.getSharedPreferences("NurseApp", MODE_PRIVATE);
        prefsEditor = prefs.edit();
        prefsEditor.putString("fcm_token", token);
        prefsEditor.apply();
    }

    //sr notification
    private void sendNotificationSr(String messageBody) {
        Intent intent;
        Log.e("notificaiton ", notificationType);
        if (notificationType=="2") {
            intent = new Intent(this, ShowOrdersSR.class);
        }
        if (notificationType.contains("3")) {
            intent = new Intent(this, TransactionSuccessfulActivity.class);
        } else {
            intent = new Intent(this, activitySplash.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "123956";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_fb)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "nurseApp",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}