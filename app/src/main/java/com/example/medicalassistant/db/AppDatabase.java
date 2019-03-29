package com.example.medicalassistant.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.medicalassistant.db.entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){

        if(instance != null){
            return instance;
        }

        return instance = Room.databaseBuilder(context,AppDatabase.class,"UserDatabase").build();
    }

    public abstract UserDAO userDAO();
}
