package com.example.medicalassistant.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.medicalassistant.db.entities.User;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("Select * from User Where unique_id LIKE :unique_id")
    User userExist(int unique_id);

    @Query("Select * from User where isCurrent = 1")
    User getCurrentUser();
}
