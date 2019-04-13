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

    public LiveData<List<Medication>> getDailyMedicationList(Context c, String day) {

        if( medicationList != null) {
            return medicationList;
        }

        if(day == "Sunday") {
            return medicationList = AppDatabase.getInstance(c).medicationDAO().getSundayMedication();
        } else if( day == "Monday") {
            return medicationList = AppDatabase.getInstance(c).medicationDAO().getMondayMedication();
        } else if( day == "Tuesday") {
            return medicationList = AppDatabase.getInstance(c).medicationDAO().getTuesdayMedication();
        } else if( day == "Wednesday") {
            return medicationList = AppDatabase.getInstance(c).medicationDAO().getWednesdayMedication();
        } else if( day == "Thursday") {
            return medicationList = AppDatabase.getInstance(c).medicationDAO().getThursdayMedication();
        } else if( day == "Friday") {
            return medicationList = AppDatabase.getInstance(c).medicationDAO().getFridayMedication();
        } else if( day == "Saturday") {
            return medicationList = AppDatabase.getInstance(c).medicationDAO().getSaturdayMedication();
        }

        return medicationList = AppDatabase.getInstance(c).medicationDAO().getCurrentDayMedication(day);
    }
}
