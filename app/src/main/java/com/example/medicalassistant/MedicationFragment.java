package com.example.medicalassistant;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationFragment extends Fragment {

    private View root;
    private FloatingActionButton fab;

    FragmentManager fm;
    public MedicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_medication, container, false);
        Log.d("TestFragment", "Navigation to medicationFragment complete");

        fm = getActivity().getSupportFragmentManager();

        fab = root.findViewById(R.id.add_medication);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm.beginTransaction()
                        .replace(R.id.main_include, new MedicationDialogFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return root;
    }

}
