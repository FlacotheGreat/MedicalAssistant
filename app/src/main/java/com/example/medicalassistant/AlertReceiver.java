package com.example.medicalassistant;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        
        createNotification(context, "Take Medication", "You have medication you need to take", "Medication");
    }

    private void createNotification(Context context, String msg, String msgText, String alert) {

        PendingIntent notificationIntent = PendingIntent.getActivity(context,0,new Intent(context, MainActivity.class),0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_nav_pill)
                                                    .setContentTitle(msg)
                                                    .setTicker(alert)
                                                    .setContentText(msgText);

        mBuilder.setContentIntent(notificationIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }
}
