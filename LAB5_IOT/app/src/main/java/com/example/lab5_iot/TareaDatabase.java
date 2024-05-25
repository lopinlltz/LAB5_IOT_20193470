package com.example.lab5_iot;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Tarea.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class TareaDatabase extends RoomDatabase {

    private static TareaDatabase instance;

    public abstract TareaDao tareaDao();

    public static synchronized TareaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TareaDatabase.class, "tarea_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
