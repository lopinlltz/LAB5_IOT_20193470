package com.example.lab5_iot;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity(tableName = "tareas")
public class Tarea implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String titulo;
    private String descripcion;
    private Date fecha;
    private Date hora;
    private boolean completada;

    public Tarea(String titulo, String descripcion, Date fecha, Date hora, boolean completada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.completada = completada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }



    public static int getImportanceLevel(Date reminderDate) {
        long reminderTime = reminderDate.getTime();
        long currentTime = System.currentTimeMillis();
        long difference = reminderTime - currentTime;
        long hoursDifference = difference / (60*60*1000);

        if (hoursDifference <= 3) {
            return NotificationCompat.PRIORITY_HIGH;
        } else {
            return NotificationCompat.PRIORITY_DEFAULT;
        }
    }
}
