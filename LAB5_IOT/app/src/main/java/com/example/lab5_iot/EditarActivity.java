package com.example.lab5_iot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class EditarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_edittask);

        Intent intent = getIntent();
        Tarea tarea = (Tarea) intent.getSerializableExtra("tarea");


    }
}
