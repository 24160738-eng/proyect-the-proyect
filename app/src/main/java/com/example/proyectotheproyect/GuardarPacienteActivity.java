package com.example.proyectotheproyect;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectotheproyect.db.ConsultaDAO;
import com.example.proyectotheproyect.db.DoctorDAO;
import com.example.proyectotheproyect.db.EgresoDAO;
import com.example.proyectotheproyect.db.PacienteDAO;
import com.example.proyectotheproyect.modelo.Consulta;
import com.example.proyectotheproyect.modelo.Doctor;
import com.example.proyectotheproyect.modelo.Egreso;
import com.example.proyectotheproyect.modelo.Paciente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GuardarPacienteActivity extends AppCompatActivity {

    private EditText etNombrePaciente, etApellidoPaterno, etApellidoMaterno, etEdadPaciente, etPeso;
    private TextView tvFechaNacimiento;
    private Button btnSeleccionarFechaNacimiento, btnGuardarPaciente;
    private Spinner spinnerGenero, spinnerDoctor;

    private ConsultaFragment consultaFragment;

    private PacienteDAO pacienteDAO;
    private ConsultaDAO consultaDAO;
    private EgresoDAO egresoDAO;
    private DoctorDAO doctorDAO;

    private List<Doctor> listaDoctores = new ArrayList<>();
    private String fechaNacimientoSeleccionada = "";

    private static final SimpleDateFormat FORMATO_FECHA =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat FORMATO_FECHA_HORA =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guardar_paciente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNombrePaciente = findViewById(R.id.etNombrePaciente);
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno);
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno);
        etEdadPaciente = findViewById(R.id.etEdadPaciente);
        etPeso = findViewById(R.id.etPeso);
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento);
        btnSeleccionarFechaNacimiento = findViewById(R.id.btnSeleccionarFechaNacimiento);
        btnGuardarPaciente = findViewById(R.id.btnGuardarPaciente);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);

        pacienteDAO = new PacienteDAO(this);
        consultaDAO = new ConsultaDAO(this);
        egresoDAO = new EgresoDAO(this);
        doctorDAO = new DoctorDAO(this);

        configurarSpinnerGenero();
        cargarDoctores();
        agregarFragmentConsulta();

        btnSeleccionarFechaNacimiento.setOnClickListener(v -> mostrarSelectorFecha());
        btnGuardarPaciente.setOnClickListener(v -> guardarTodo());
    }

    private void configurarSpinnerGenero() {
        String[] generos = {"Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, generos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapter);
    }

    private void cargarDoctores() {
        listaDoctores = doctorDAO.obtenerTodos();
        ArrayAdapter<Doctor> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listaDoctores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);
    }

    private void agregarFragmentConsulta() {
        consultaFragment = new ConsultaFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameConsulta, consultaFragment)
                .commit();
    }

    private void mostrarSelectorFecha() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar seleccionado = Calendar.getInstance();
                    seleccionado.set(year, month, dayOfMonth);
                    fechaNacimientoSeleccionada = FORMATO_FECHA.format(seleccionado.getTime());
                    tvFechaNacimiento.setText(fechaNacimientoSeleccionada);
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void guardarTodo() {
        String nombre = etNombrePaciente.getText().toString().trim();
        String apellidoP = etApellidoPaterno.getText().toString().trim();
        String apellidoM = etApellidoMaterno.getText().toString().trim();
        String edadStr = etEdadPaciente.getText().toString().trim();
        String pesoStr = etPeso.getText().toString().trim();

        if (nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty()
                || edadStr.isEmpty() || pesoStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos del paciente", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fechaNacimientoSeleccionada.isEmpty()) {
            Toast.makeText(this, "Selecciona la fecha de nacimiento", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listaDoctores.isEmpty()) {
            Toast.makeText(this, "No hay doctores registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        String motivo = consultaFragment.getMotivo();
        if (motivo.isEmpty()) {
            Toast.makeText(this, "Escribe el motivo de la consulta", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!consultaFragment.isHoraSalidaSeleccionada()) {
            Toast.makeText(this, "Selecciona la hora de salida", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad;
        double peso;
        try {
            edad = Integer.parseInt(edadStr);
            peso = Double.parseDouble(pesoStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad o peso inválidos", Toast.LENGTH_SHORT).show();
            return;
        }

        String genero = spinnerGenero.getSelectedItem().toString();
        Doctor doctorSeleccionado = (Doctor) spinnerDoctor.getSelectedItem();
        String ahora = FORMATO_FECHA_HORA.format(Calendar.getInstance().getTime());

        // 1. Guardar paciente
        Paciente paciente = new Paciente(0, nombre, apellidoP, apellidoM,
                fechaNacimientoSeleccionada, edad, genero, peso, ahora, ahora);
        long idPacienteGenerado = pacienteDAO.insertarPaciente(paciente);

        if (idPacienteGenerado == -1) {
            Toast.makeText(this, "Error al guardar el paciente", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Guardar consulta (usando el id del paciente recién insertado)
        Consulta consulta = new Consulta(0,
                "Ninguna registrada",   // alergias (no capturado en el form)
                motivo,                  // observaciones_sintomas
                "Pendiente de diagnóstico", // diagnostico (no capturado en el form)
                ahora,
                (int) idPacienteGenerado,
                doctorSeleccionado.getIdDoctor());
        consultaDAO.insertarConsulta(consulta);

        // 3. Guardar egreso (hora de salida capturada en el Fragment)
        Egreso egreso = new Egreso(0,
                "Sin observaciones",
                ahora,
                (int) idPacienteGenerado,
                consultaFragment.getHoraSalida());
        egresoDAO.insertarEgreso(egreso);

        Toast.makeText(this, "Paciente y consulta guardados correctamente", Toast.LENGTH_LONG).show();
        finish();
    }
}