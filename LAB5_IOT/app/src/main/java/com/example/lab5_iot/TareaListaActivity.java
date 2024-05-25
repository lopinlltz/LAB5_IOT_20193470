package com.example.lab5_iot;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TareaListaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TareaAdapter adapter;
    private TareaRepository tareaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklist_activity);

        recyclerView = findViewById(R.id.recyclerView_tasklist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TareaAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        tareaRepository = new TareaRepository(getApplication());
        tareaRepository.getAllTareas().observe(this, new Observer<List<Tarea>>() {
            @Override
            public void onChanged(List<Tarea> tareas) {
                adapter.setTareaList(tareas);
                mostrarNotificaciones(tareas);
            }
        });

        Button buttonCrear = findViewById(R.id.buttonCrear);
        buttonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TareaListaActivity.this, CrearActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarNotificaciones(List<Tarea> tareas) {
        for (Tarea tarea : tareas) {
            if (tareaCercana(tarea)) {
                construirNotificacion(tarea);
            }
        }
    }

    private boolean tareaCercana(Tarea tarea) {
        Calendar calendarioActual = Calendar.getInstance();
        long tiempoActual = calendarioActual.getTimeInMillis();

        Calendar calendarioTarea = Calendar.getInstance();
        calendarioTarea.setTime(tarea.getFecha());
        calendarioTarea.set(Calendar.HOUR_OF_DAY, tarea.getHora().getHours());
        calendarioTarea.set(Calendar.MINUTE, tarea.getHora().getMinutes());
        long tiempoTarea = calendarioTarea.getTimeInMillis();

        long diferencia = tiempoTarea - tiempoActual;
        long tiempoLimite = 3*60*60*1000;
        if (diferencia <= tiempoLimite) {
            return true;
        } else {
            return false;
        }
    }

    private void construirNotificacion(Tarea tarea) {
        String titulo = tarea.getTitulo();
        String descripcion = tarea.getDescripcion();
        int id = tarea.getId();
        Intent intent = new Intent(this, TareaListaActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelId")
                .setSmallIcon(R.drawable.alarmpic)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //NotificationManager.notify(id, builder.build());
    }


}
