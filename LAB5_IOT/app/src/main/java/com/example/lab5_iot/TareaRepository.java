package com.example.lab5_iot;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TareaRepository extends AndroidViewModel {
    private TareaDao tareaDao;
    private LiveData<List<Tarea>> allTareas;
    private ExecutorService executorService;

    public TareaRepository(@NonNull Application application) {
        super(application);
        TareaDatabase db = TareaDatabase.getInstance(application);
        tareaDao = db.tareaDao();
        allTareas = tareaDao.getAllTareas();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Tarea>> getAllTareas() {
        return allTareas;
    }


    public void insert(Tarea tarea) {
        executorService.execute(() -> tareaDao.insert(tarea));
    }

    public void update(Tarea tarea) {
        executorService.execute(() -> tareaDao.update(tarea));
    }

    public void delete(Tarea tarea) {
        executorService.execute(() -> tareaDao.delete(tarea));
    }

    public void deleteAllTareas() {
        executorService.execute(() -> tareaDao.deleteAllTareas());
    }

}
