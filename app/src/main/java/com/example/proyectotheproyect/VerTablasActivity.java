package com.example.proyectotheproyect;

import static androidx.core.content.ContextCompat.startActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectotheproyect.db.DatabaseHelper;
import com.example.proyectotheproyect.db.TablaDAO;

public class VerTablasActivity extends AppCompatActivity {

    private Spinner spinnerTablas;
    private Button btnVolverVerPacientes;
    private LinearLayout layoutEncabezados;
    private RecyclerView rvContenidoTabla;
    private TextView tvTablaVacia;

    private TablaDAO tablaDAO;

    private final String[] nombresVisibles = {
            "Pacientes", "Doctores", "Consultas", "Egresos", "Recetas", "Detalle de receta", "Usuarios"
    };

    private final String[] nombresReales = {
            DatabaseHelper.TABLE_PACIENTES,
            DatabaseHelper.TABLE_DOCTOR,
            DatabaseHelper.TABLE_CONSULTAS,
            DatabaseHelper.TABLE_EGRESOS,
            DatabaseHelper.TABLE_RECETAS,
            DatabaseHelper.TABLE_DETALLE_RECETA,
            DatabaseHelper.TABLE_USUARIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_tablas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerTablas = findViewById(R.id.spinnerTablas);
        layoutEncabezados = findViewById(R.id.layoutEncabezados);
        rvContenidoTabla = findViewById(R.id.rvContenidoTabla);
        tvTablaVacia = findViewById(R.id.tvTablaVacia);

        btnVolverVerPacientes = findViewById(R.id.btnVolverVerPacientes);
        btnVolverVerPacientes.setOnClickListener(v -> {
            startActivity(new Intent(VerTablasActivity.this, VerPacientesActivity.class));
            finish();
        });

        rvContenidoTabla.setLayoutManager(new LinearLayoutManager(this));
        tablaDAO = new TablaDAO(this);

        configurarSpinner();
    }

    private void configurarSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nombresVisibles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTablas.setAdapter(adapter);

        spinnerTablas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                cargarTabla(nombresReales[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void cargarTabla(String nombreTabla) {
        TablaDAO.ResultadoTabla resultado = tablaDAO.obtenerContenido(nombreTabla);

        pintarEncabezados(resultado.columnas);

        if (resultado.filas.isEmpty()) {
            tvTablaVacia.setVisibility(TextView.VISIBLE);
            rvContenidoTabla.setVisibility(RecyclerView.GONE);
        } else {
            tvTablaVacia.setVisibility(TextView.GONE);
            rvContenidoTabla.setVisibility(RecyclerView.VISIBLE);
            rvContenidoTabla.setAdapter(new TablaAdapter(resultado.filas, resultado.columnas.size()));
        }
    }

    private void pintarEncabezados(java.util.List<String> columnas) {
        layoutEncabezados.removeAllViews();
        for (String nombreColumna : columnas) {
            TextView tv = new TextView(this);
            tv.setText(nombreColumna);
            tv.setTextSize(12);
            tv.setTypeface(null, android.graphics.Typeface.BOLD);
            tv.setGravity(Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            tv.setLayoutParams(params);

            layoutEncabezados.addView(tv);
        }
    }
}