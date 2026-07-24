package com.example.proyectotheproyect;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TablaAdapter extends RecyclerView.Adapter<TablaAdapter.FilaViewHolder> {

    private List<List<String>> filas;
    private int numeroColumnas;

    public TablaAdapter(List<List<String>> filas, int numeroColumnas) {
        this.filas = filas;
        this.numeroColumnas = numeroColumnas;
    }

    @NonNull
    @Override
    public FilaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fila_tabla, parent, false);
        return new FilaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull FilaViewHolder holder, int position) {
        List<String> fila = filas.get(position);
        holder.llFila.removeAllViews();

        for (int i = 0; i < numeroColumnas; i++) {
            TextView tv = new TextView(holder.llFila.getContext());
            tv.setText(i < fila.size() ? fila.get(i) : "");
            tv.setTextSize(12);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setPadding(4, 4, 4, 4);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            tv.setLayoutParams(params);

            holder.llFila.addView(tv);
        }
    }

    @Override
    public int getItemCount() {
        return filas.size();
    }

    static class FilaViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llFila;

        public FilaViewHolder(@NonNull View itemView) {
            super(itemView);
            llFila = itemView.findViewById(R.id.llFila);
        }
    }
}