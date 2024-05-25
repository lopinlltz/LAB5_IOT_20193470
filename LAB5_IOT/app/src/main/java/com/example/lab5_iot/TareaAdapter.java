package com.example.lab5_iot;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.ViewHolder> {
    private List<Tarea> tareaList;
    private Context context;

    public TareaAdapter( Context context  , List<Tarea> historialList) {
        this.tareaList = historialList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarea tarea = tareaList.get(position);
        holder.tituloTextView.setText(tarea.getTitulo());
        holder.fechaTextView.setText(tarea.getFecha().toString());
        holder.horaTextView.setText(tarea.getHora().toString());
        holder.checkboxBoolean.setChecked(tarea.isCompletada());

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarActivity.class);
            intent.putExtra("tarea", tarea);
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("¿Estás seguro de que desea eliminar esta tarea?")
                    .setPositiveButton("Sí", (dialog, id) -> {
                        TareaRepository tareaRepository = new TareaRepository(((Application) context.getApplicationContext()));
                        tareaRepository.delete(tarea);
                    })
                    .setNegativeButton("No", null)
                    .create()
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return tareaList.size();
    }

    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fechaTextView;
        TextView horaTextView;
        TextView tituloTextView;
        CheckBox checkboxBoolean;
        ImageButton editButton;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fechaTextView = itemView.findViewById(R.id.textViewFecha);
            horaTextView = itemView.findViewById(R.id.textViewHora);
            tituloTextView = itemView.findViewById(R.id.textViewTitulo);
            checkboxBoolean = itemView.findViewById(R.id.checkbox_complete);
            editButton = itemView.findViewById(R.id.edit_task);
            deleteButton = itemView.findViewById(R.id.delete_task);
        }
    }
}

