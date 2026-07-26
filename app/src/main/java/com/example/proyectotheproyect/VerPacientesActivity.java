package com.example.proyectotheproyect;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectotheproyect.db.PacienteDAO;
import com.example.proyectotheproyect.modelo.PacienteConDoctor;

import java.util.List;
import android.content.Intent;
import android.widget.Button;

public class VerPacientesActivity extends AppCompatActivity {

    private RecyclerView rvPacientes;
    private TextView tvSinPacientes;
    private Button btnVolverMenu;
    private PacienteDAO pacienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_pacientes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVolverMenu = findViewById(R.id.btnVolverMenu);
        btnVolverMenu.setOnClickListener(v -> {
            startActivity(new Intent(VerPacientesActivity.this, MenuActivity.class));
            finish();
        });

        rvPacientes = findViewById(R.id.rvPacientes);
        tvSinPacientes = findViewById(R.id.tvSinPacientes);

        rvPacientes.setLayoutManager(new LinearLayoutManager(this));
        pacienteDAO = new PacienteDAO(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarPacientes();
    }

    private void cargarPacientes() {
        List<PacienteConDoctor> lista = pacienteDAO.obtenerTodosConDoctor();

        if (lista.isEmpty()) {
            tvSinPacientes.setVisibility(TextView.VISIBLE);
            rvPacientes.setVisibility(TextView.GONE);
        } else {
            tvSinPacientes.setVisibility(TextView.GONE);
            rvPacientes.setVisibility(TextView.VISIBLE);
            rvPacientes.setAdapter(new PacienteAdapter(lista));
        }
    }
}