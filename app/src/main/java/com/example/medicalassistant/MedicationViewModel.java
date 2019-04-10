package com.example.medicalassistant;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.medicalassistant.db.AppDatabase;
import com.example.medicalassistant.db.entities.Medication;

import java.util.List;

public class MedicationViewModel extends ViewModel {

    private LiveData<List<Medication>> medicationList;

    public LiveData<List<Medication>> getMedicationList(Context c) {

        if(medicationList != null){
            return medicationList;
        }

        return medicationList = AppDatabase.getInstance(c).medicationDAO().getAllMedication();
    }

    public LiveData<List<Medication>> getDailyMedicationList(Context c) {



        return medicationList;
    }
}
