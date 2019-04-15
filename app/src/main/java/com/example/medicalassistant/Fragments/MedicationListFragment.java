package com.example.medicalassistant.Fragments;



import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalassistant.Adapter.MedicationAdapter;
import com.example.medicalassistant.MedicationViewModel;
import com.example.medicalassistant.R;
import com.example.medicalassistant.db.entities.Medication;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationListFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private MedicationAdapter adapter;
    private int columnCount = 1;
    private String day;

    public MedicationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_medication_list, container, false);

        recyclerView = root.findViewById(R.id.medicationRecyclerview);



        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        final Context context = getContext();
        adapter = new MedicationAdapter(new ArrayList<Medication>());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);

        Log.d("TestLayoutPressed", "Day received:" + day);
        //Handles data that is passed between events

            ViewModelProviders.of(this)
                    .get(MedicationViewModel.class)
                    .getDailyMedicationList(context,day)
                    .observe(this, new Observer<List<Medication>>() {
                        @Override
                        public void onChanged(@Nullable List<Medication> medications) {
                            if (medications != null) {
                                adapter.addItems(medications);
                            }
                        }
                    });
        }

    public void getDay(String day){
        this.day = day;
    }
}