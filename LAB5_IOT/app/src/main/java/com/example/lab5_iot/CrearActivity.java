package com.example.lab5_iot;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class CrearActivity extends AppCompatActivity {
    private EditText editTextTitulo, editTextDescripcion, editTextFecha, editTextHora;
    private Button buttonCrearTarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_newtask);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextHora = findViewById(R.id.editTextHora);
        buttonCrearTarea = findViewById(R.id.buttonCrearTarea);

        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        editTextHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        buttonCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTextTitulo.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                //el resto de este metodo fue scado de chatgpt para poder usar los datos de fecha y hora
                Calendar calendar = Calendar.getInstance();

                String[] fechaParts = editTextFecha.getText().toString().split("-");
                int year = Integer.parseInt(fechaParts[0]);
                int month = Integer.parseInt(fechaParts[1]) - 1;
                int day = Integer.parseInt(fechaParts[2]);
                calendar.set(year, month, day);

                String[] horaParts = editTextHora.getText().toString().split(":");
                int hourOfDay = Integer.parseInt(horaParts[0]);
                int minute = Integer.parseInt(horaParts[1]);
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                Date fecha = calendar.getTime();
                Date hora = calendar.getTime();
                Tarea nuevaTarea = new Tarea(titulo, descripcion, fecha, hora, false);

                TareaRepository tareaRepository = new TareaRepository(getApplication());
                tareaRepository.insert(nuevaTarea);

                finish();
            }
        });

    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> editTextFecha.setText(LocalDate.of(year, month + 1, dayOfMonth).toString()),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> editTextHora.setText(LocalTime.of(hourOfDay, minute).toString()),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }
}
