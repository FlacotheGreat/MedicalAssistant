package com.example.medicalassistant;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.medicalassistant.Fragments.MedicationListFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationFragment extends Fragment {

    private View root;
    private FloatingActionButton fab;
    private LinearLayout layoutSunday, layoutMonday, layoutTuesday,
            layoutWednesday, layoutThursday, layoutFriday, layoutSaturday;
    private OnDayClicked mCallBack;

    interface OnDayClicked {

        void getMedsForDay(String day);

    }

    @Override
    public void onAttach(Activity activity) {

        try{
            mCallBack = (OnDayClicked) activity;
        } catch (ClassCastException cce){
            throw new ClassCastException(activity.toString() + " Must implement OnDayClicked");
        }
        super.onAttach(activity);
    }

    FragmentManager fm;
    public MedicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_medication, container, false);
        Log.d("TestFragment", "Navigation to medicationFragment complete");

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        fm = getActivity().getSupportFragmentManager();

        fab = root.findViewById(R.id.add_medication);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm.beginTransaction()
                        .replace(R.id.main_include, new MedicationDialogFragment())
                        .commit();
            }
        });

        layoutSunday = root.findViewById(R.id.layoutSunday);
        layoutMonday = root.findViewById(R.id.layoutMonday);
        layoutTuesday= root.findViewById(R.id.layoutTuesday);
        layoutWednesday = root.findViewById(R.id.layoutWednesday);
        layoutThursday = root.findViewById(R.id.layoutThursday);
        layoutFriday = root.findViewById(R.id.layoutFriday);
        layoutSaturday = root.findViewById(R.id.layoutSaturday);

        View.OnClickListener dayClicked = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = (LinearLayout) view;

                switch (view.getId()){

                    case R.id.layoutSunday:
                        Log.d("TestLayoutPress", "Sunday's layout pressed");
                        mCallBack.getMedsForDay("Sunday");
                        break;
                    case R.id.layoutMonday:
                        Log.d("TestLayoutPress", "Monday's layout pressed");
                        mCallBack.getMedsForDay("Monday");
                        break;
                    case R.id.layoutTuesday:
                        Log.d("TestLayoutPress", "Tuesday's layout pressed");
                        mCallBack.getMedsForDay("Tuesday");
                        break;
                    case R.id.layoutWednesday:
                        Log.d("TestLayoutPress", "Wednesday's layout pressed");
                        mCallBack.getMedsForDay("Wednesday");
                        break;
                    case R.id.layoutThursday:
                        Log.d("TestLayoutPress", "Thursday's layout pressed");
                        mCallBack.getMedsForDay("Thursday");
                        break;
                    case R.id.layoutFriday:
                        Log.d("TestLayoutPress", "Friday's layout pressed");
                        mCallBack.getMedsForDay("Friday");
                        break;
                    case R.id.layoutSaturday:
                        Log.d("TestLayoutPress", "Saturday's layout pressed");
                        mCallBack.getMedsForDay("Saturday");
                        break;

                }

            }

        };

        layoutSunday.setOnClickListener(dayClicked);
        layoutMonday.setOnClickListener(dayClicked);
        layoutTuesday.setOnClickListener(dayClicked);
        layoutWednesday.setOnClickListener(dayClicked);
        layoutThursday.setOnClickListener(dayClicked);
        layoutFriday.setOnClickListener(dayClicked);
        layoutSaturday.setOnClickListener(dayClicked);

    }
}
