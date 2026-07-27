package com.example.proyectotheproyect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    private Button btnMenuWifiInfo, btnMenuLocalizacion, btnMenuGuardarPaciente,
            btnMenuVerPacientes, btnMenuVerTablas, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnMenuWifiInfo = findViewById(R.id.btnMenuWifiInfo);
        btnMenuLocalizacion = findViewById(R.id.btnMenuLocalizacion);
        btnMenuGuardarPaciente = findViewById(R.id.btnMenuGuardarPaciente);
        btnMenuVerPacientes = findViewById(R.id.btnMenuVerPacientes);
        btnMenuVerTablas = findViewById(R.id.btnMenuVerTablas);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnMenuWifiInfo.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, WifiInfoActivity.class)));

        btnMenuLocalizacion.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, LocalizacionActivity.class)));

        btnMenuGuardarPaciente.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, GuardarPacienteActivity.class)));

        btnMenuVerPacientes.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, VerPacientesActivity.class)));

        btnMenuVerTablas.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, VerTablasActivity.class)));

        startService(new Intent(this, MusicaService.class));

        btnCerrarSesion.setOnClickListener(v -> {
            stopService(new Intent(this, MusicaService.class));
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}