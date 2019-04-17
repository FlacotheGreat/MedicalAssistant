package com.example.medicalassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DateTimeChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

            Log.d("TestDateTimeChange","Hey it's 1:05AM");
            if (action.equals(Intent.ACTION_TIME_CHANGED) ||
                    action.equals(Intent.ACTION_TIMEZONE_CHANGED) ||
                    action.equals(Intent.ACTION_DATE_CHANGED))
            {

            }
        }
}
