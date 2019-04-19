package com.example.medicalassistant.Fragments;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalassistant.Adapter.HomeMedicationAdapter;
import com.example.medicalassistant.Adapter.MedicationAdapter;
import com.example.medicalassistant.AlertReceiver;
import com.example.medicalassistant.Constants;
import com.example.medicalassistant.DialogFragments.EditUserDialogFragment;
import com.example.medicalassistant.MedicationViewModel;
import com.example.medicalassistant.R;
import com.example.medicalassistant.db.AppDatabase;
import com.example.medicalassistant.db.entities.Medication;
import com.example.medicalassistant.db.entities.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View root;
    private User user;

    private ImageView homeImage;
    private TextView homeFName,homeLName,homeEContactName, homeEContactNumber, homePhyscian;
    private TextView physicianText, eContactText;
    private Button homeCreateUser;
    private FragmentManager fm;
    private HomeMedicationAdapter adapter;
    private RecyclerView homeRecyclerView;
    private String day;

    private Context mContext;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_top_home, container, false);

        fm = getActivity().getSupportFragmentManager();

        mContext = getContext();
        getCurrentUser();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentUser();
        homeImage = root.findViewById(R.id.home_user_image);
        homeFName = root.findViewById(R.id.home_first_name);
        homeLName = root.findViewById(R.id.home_last_name);
        homeEContactNumber = root.findViewById(R.id.home_contact_number);
        homeEContactName = root.findViewById(R.id.home_emergency_name);
        homePhyscian = root.findViewById(R.id.home_physcian_name);
        homeRecyclerView = root.findViewById(R.id.home_recycler);
        physicianText = root.findViewById(R.id.home_physician);
        eContactText = root.findViewById(R.id.home_emergency_contact);

        homeCreateUser = root.findViewById(R.id.home_create_user);
        homeCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditUserDialogFragment editUserDialogFragment= new EditUserDialogFragment();
//                fm.beginTransaction()
//                        .replace(R.id.main_include, new EditUserDialogFragment())
//                        .addToBackStack(null)
//                        .commit();
                editUserDialogFragment.show(fm,"ShowDialog");
            }
        });

        if(user != null)
        {
            homeCreateUser.setVisibility(View.INVISIBLE);
            homeImage.setImageURI(Uri.parse(user.getUser_image()));
            homeFName.setText(user.getFname());
            homeLName.setText(user.getLname());
            homeEContactName.setText(user.geteContactName());
            homeEContactNumber.setText(user.geteContactNum());
            homePhyscian.setText(user.getPhysicianName());

            homeImage.setVisibility(View.VISIBLE);
            homeFName.setVisibility(View.VISIBLE);
            homeLName.setVisibility(View.VISIBLE);
            homeEContactNumber.setVisibility(View.VISIBLE);
            homeEContactName.setVisibility(View.VISIBLE);
            homePhyscian.setVisibility(View.VISIBLE);
        } else {
            homeCreateUser.setVisibility(View.VISIBLE);
            homeImage.setVisibility(View.INVISIBLE);
            homeFName.setVisibility(View.INVISIBLE);
            homeLName.setVisibility(View.INVISIBLE);
            homeEContactNumber.setVisibility(View.INVISIBLE);
            homeEContactName.setVisibility(View.INVISIBLE);
            homePhyscian.setVisibility(View.INVISIBLE);
            physicianText.setVisibility(View.INVISIBLE);
            eContactText.setVisibility(View.INVISIBLE);
        }


        final Context context = getContext();
        adapter = new HomeMedicationAdapter(new ArrayList<Medication>());

        homeRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        homeRecyclerView.setAdapter(adapter);
        homeRecyclerView.setHasFixedSize(false);

        //Handles data that is passed between events
        getDay();
        Log.d("TestHomeFragment", "Day received:" + day);
        ViewModelProviders.of(this)
                .get(MedicationViewModel.class)
                .getDailyMedicationList(context, Constants.DAY)
                .observe(this, new Observer<List<Medication>>() {
                    @Override
                    public void onChanged(@Nullable List<Medication> medications) {
                        if (medications != null) {
                            adapter.addItems(medications);
                            setDailyAlarms(medications);
                        }
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getDay(){
        Calendar c =  Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);

        switch(day){
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

    public void setUser(User user) {

        this.user = user;
    }

    private void getCurrentUser() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(AppDatabase.getInstance(getContext()).userDAO().getCurrentUser() != null){
                     setUser(AppDatabase.getInstance(getContext()).userDAO().getCurrentUser());
                }
            }
        }).start();
    }

    private void setDailyAlarms(List<Medication> medications){

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar Calendar3 = Calendar.getInstance();


        for(Medication m: medications) {

            if (m.getFrequency() == 1){
                Log.d("TestFrequency", "We are looking at frequency as we speak");
                calendar1.set(Calendar.HOUR, 6);
                calendar1.set(Calendar.MINUTE,5);
                calendar1.set(Calendar.SECOND,0);

                setAlarm(root,calendar1);

            } else if (m.getFrequency() == 2) {
                Log.d("TestFrequency", "We are looking at frequency as we speak");

                calendar1.set(Calendar.HOUR, 6);
                calendar1.set(Calendar.MINUTE,10);
                calendar1.set(Calendar.SECOND,0);
                setAlarm(root,calendar1);

                calendar2.set(Calendar.HOUR, 8);
                calendar2.set(Calendar.MINUTE,2);
                calendar2.set(Calendar.SECOND,0);

                setAlarm(root,calendar2);
            } else if (m.getFrequency() == 3) {
                Log.d("TestFrequency", "We are looking at frequency as we speak");


            }

        }

    }


    private void setAlarm(View view, Calendar c)
    {

        Log.d("TestSetAlarm","Setting alarm");

        Intent alertIntent = new Intent(getActivity(), AlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);

//        if(c.before(Calendar.getInstance())){
//            c.add(Calendar.DATE,1);
//        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                PendingIntent.getBroadcast(view.getContext(),1,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));

    }

}
