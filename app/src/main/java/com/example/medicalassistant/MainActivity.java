package com.example.medicalassistant;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.medicalassistant.Fragments.AppointmentFragment;
import com.example.medicalassistant.Fragments.MedicationListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                               MedicationFragment.OnDayClicked {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_include, new TopHomeFragment())
                .addToBackStack(null)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                    .replace(R.id.main_include, new TopHomeFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_appointments) {

            fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.main_include, new AppointmentFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_medication) {

            fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.main_include, new MedicationFragment()).addToBackStack(null).commit();

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
}
