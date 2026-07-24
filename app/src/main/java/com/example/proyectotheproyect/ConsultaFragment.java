package com.example.proyectotheproyect;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Locale;

public class ConsultaFragment extends Fragment {

    private EditText etMotivoConsulta;
    private TextView tvHoraEntrada, tvHoraSalida;
    private Button btnSeleccionarHoraSalida;

    private String horaEntrada = "";
    private String horaSalida = "";

    public ConsultaFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consulta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etMotivoConsulta = view.findViewById(R.id.etMotivoConsulta);
        tvHoraEntrada = view.findViewById(R.id.tvHoraEntrada);
        tvHoraSalida = view.findViewById(R.id.tvHoraSalida);
        btnSeleccionarHoraSalida = view.findViewById(R.id.btnSeleccionarHoraSalida);

        // Hora de entrada = hora actual, automática
        Calendar ahora = Calendar.getInstance();
        horaEntrada = String.format(Locale.getDefault(), "%02d:%02d",
                ahora.get(Calendar.HOUR_OF_DAY), ahora.get(Calendar.MINUTE));
        tvHoraEntrada.setText(horaEntrada);

        btnSeleccionarHoraSalida.setOnClickListener(v -> mostrarSelectorHora());
    }

    private void mostrarSelectorHora() {
        Calendar c = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    horaSalida = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    tvHoraSalida.setText(horaSalida);
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    public String getMotivo() {
        return etMotivoConsulta.getText().toString().trim();
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public boolean isHoraSalidaSeleccionada() {
        return horaSalida != null && !horaSalida.isEmpty();
    }
}