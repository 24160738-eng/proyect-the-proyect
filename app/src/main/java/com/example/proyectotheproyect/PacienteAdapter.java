package com.example.proyectotheproyect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectotheproyect.modelo.PacienteConDoctor;

import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder> {

    private List<PacienteConDoctor> listaPacientes;

    public PacienteAdapter(List<PacienteConDoctor> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_paciente, parent, false);
        return new PacienteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int position) {
        PacienteConDoctor p = listaPacientes.get(position);

        String nombreCompleto = p.getNombre() + " " + p.getApellidoP() + " " + p.getApellidoM();
        holder.tvNombreItem.setText(nombreCompleto);

        holder.tvEdadTelefonoItem.setText("Edad: " + p.getEdad() + " | Tel: --");

        String doctor = p.getNombreDoctor();
        if (doctor == null || doctor.isEmpty()) {
            holder.tvDoctorItem.setText("Atendido por: --");
        } else {
            holder.tvDoctorItem.setText("Atendido por: " + doctor);
        }
    }

    @Override
    public int getItemCount() {
        return listaPacientes.size();
    }

    static class PacienteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreItem, tvEdadTelefonoItem, tvDoctorItem;

        public PacienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreItem = itemView.findViewById(R.id.tvNombreItem);
            tvEdadTelefonoItem = itemView.findViewById(R.id.tvEdadTelefonoItem);
            tvDoctorItem = itemView.findViewById(R.id.tvDoctorItem);
        }
    }
}