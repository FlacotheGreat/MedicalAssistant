package com.example.medicalassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.medicalassistant.Fragments.HomeFragment;

import java.util.Calendar;

public class DateTimeChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

            Log.d("TestDateTimeChange","Hey it's 1:05AM");
            if (action.equals(Intent.ACTION_TIME_CHANGED) ||
                action.equals(Intent.ACTION_TIMEZONE_CHANGED) ||
                action.equals(Intent.ACTION_DATE_CHANGED)) {
                setNewDay();
                Log.d("TestDays","Day = "+ Constants.DAY);
            }
        }

  void setNewDay()
  {
      switch(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
          case Calendar.SUNDAY:
              Constants.DAY = "Sunday";
              break;
          case Calendar.MONDAY:
              Constants.DAY = "Monday";
              break;
          case Calendar.TUESDAY:
              Constants.DAY = "Tuesday";
              break;
          case Calendar.WEDNESDAY:
              Constants.DAY = "Wednesday";
              break;
          case Calendar.THURSDAY:
              Constants.DAY = "Thursday";
              break;
          case Calendar.FRIDAY:
              Constants.DAY = "Friday";
              break;
          case Calendar.SATURDAY:
              Constants.DAY = "Saturday";
              break;
      }
  }
}
