package com.example.medicalassistant;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.medicalassistant.Fragments.AppointmentFragment;
import com.example.medicalassistant.DialogFragments.EditUserDialogFragment;
import com.example.medicalassistant.Fragments.HomeFragment;
import com.example.medicalassistant.Fragments.MedicationFragment;
import com.example.medicalassistant.Fragments.MedicationListFragment;
import com.example.medicalassistant.db.entities.User;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

//import androidx.work.PeriodicWorkRequest;
//import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                               MedicationFragment.OnDayClicked,
                                                               EditUserDialogFragment.returnuser{
    public static boolean IS_USER_SET = false;
    private FragmentManager fm;
    HomeFragment homeFragment;

    int notifID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeFragment = new HomeFragment();

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_include, homeFragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        PeriodicWorkRequest.Builder request = new PeriodicWorkRequest.Builder(MyWorker.class, 1, TimeUnit.MINUTES);
////
////        PeriodicWorkRequest work = request.build();
////        WorkManager.getInstance().enqueue(work);

        runGetDayManager();
    }

    private void runGetDayManager() {

        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,DateTimeChangeReceiver.class);
        PendingIntent mAlarmSender = PendingIntent.getService(this,20, intent, 0);

        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 8);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,mAlarmSender);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {

            fm.beginTransaction()
                    .replace(R.id.main_include, new EditUserDialogFragment())
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.main_include, homeFragment)
                    .commit();

        } else if (id == R.id.nav_appointments) {

            fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.main_include, new AppointmentFragment())
                    .commit();

        } else if (id == R.id.nav_medication) {

            fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.main_include, new MedicationFragment())
                    .addToBackStack(null)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getMedsForDay(String day) {

        Log.d("Testlayout", "Made it to main with:" + day);
        MedicationListFragment medicationListFragment = new MedicationListFragment();
        medicationListFragment.getDay(day);

        fm.beginTransaction()
                .replace(R.id.main_include, medicationListFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void returnUser(User user) {

        homeFragment.setUser(user);
    }

    public void showNotification(View view){

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Medication Reminder")
                .setContentText("Don't forget to take your Medication")
                .setTicker("Medication")
                .setSmallIcon(R.drawable.ic_nav_pill);

        //Intent moreInfoIntent = new Intent(this, )
    }

}
