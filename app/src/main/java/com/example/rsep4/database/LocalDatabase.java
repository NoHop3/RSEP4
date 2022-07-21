package com.example.rsep4.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.rsep4.models.UserModel;
import com.example.rsep4.models.WeatherModel;

@Database(entities = {WeatherModel.class, UserModel.class}, version = 4)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase instance;
    public abstract WeatherDAO weatherDAO();
    public abstract UserDAO userDAO();

    public static synchronized LocalDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                    LocalDatabase.class, "local_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
