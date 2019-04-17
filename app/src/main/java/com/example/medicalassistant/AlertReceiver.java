package com.example.medicalassistant;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

//import static android.support.v4.content.ContextCompat.getSystemService;

public class AlertReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "101";
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        createNotification(context, "Take Medication", "You have medication you need to take", "Medication");
    }

    private void createNotification(Context context, String msg, String msgText, String alert) {

        createNotificationChannel();
        PendingIntent notificationIntent = PendingIntent.getActivity(context,0,new Intent(context, MainActivity.class),0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                                    .setSmallIcon(R.drawable.medicine)
                                                    .setContentTitle(msg)
                                                    .setTicker(alert)
                                                    .setContentText(msgText)
                                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        mBuilder.setContentIntent(notificationIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = mContext.getString(R.string.channel_name);
            String description = mContext.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
