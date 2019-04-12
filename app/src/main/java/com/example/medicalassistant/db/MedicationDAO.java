package com.example.medicalassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.medicalassistant.db.entities.Medication;

import java.util.List;

@Dao
public interface MedicationDAO {

    @Insert
    void insertMedication(Medication medication);

    @Delete
    void deleteMedication(Medication medication);

    @Update
    void updateMedication(Medication medication);

    @Query("Select * from Medication Where Sunday = 1")
    LiveData<List<Medication>> getAllMedication();

    @Query("Select * from Medication Where daily = 1")
    LiveData<List<Medication>> getCurrentDayMedication();
}
