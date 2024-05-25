package com.example.lab5_iot;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TareaDao {

    @Insert
    void insert(Tarea tarea);

    @Update
    void update(Tarea tarea);

    @Delete
    void delete(Tarea tarea);
    @Query("DELETE FROM tareas")
    void deleteAllTareas();

    @Query("SELECT * FROM tareas ORDER BY fecha DESC")
    LiveData<List<Tarea>> getAllTareas();

}

