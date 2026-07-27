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
import android.content.Intent;

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
import com.example.proyectotheproyect.util.ValidacionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GuardarPacienteActivity extends AppCompatActivity {

    private EditText etNombrePaciente, etApellidoPaterno, etApellidoMaterno, etPeso;
    private TextView tvFechaNacimiento;
    private TextView tvEdadCalculada;
    private Button btnSeleccionarFechaNacimiento, btnGuardarPaciente, btnVolverMenu;
    private Spinner spinnerGenero, spinnerDoctor;
    private ConsultaFragment consultaFragment;

    private PacienteDAO pacienteDAO;
    private ConsultaDAO consultaDAO;
    private EgresoDAO egresoDAO;
    private DoctorDAO doctorDAO;

    private List<Doctor> listaDoctores = new ArrayList<>();
    private String fechaNacimientoSeleccionada = "";
    private int edadCalculada = -1;

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
        etPeso = findViewById(R.id.etPeso);
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento);
        tvEdadCalculada = findViewById(R.id.tvEdadCalculada);
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

        btnVolverMenu = findViewById(R.id.btnVolverMenu);
        btnVolverMenu.setOnClickListener(v -> {
            startActivity(new Intent(GuardarPacienteActivity.this, MenuActivity.class));
            finish();
        });
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

                    edadCalculada = calcularEdad(seleccionado);
                    tvEdadCalculada.setText(String.valueOf(edadCalculada));
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );
        // No permite seleccionar una fecha posterior a hoy
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    private int calcularEdad(Calendar fechaNacimiento) {
        Calendar hoy = Calendar.getInstance();
        int edad = hoy.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);

        // Ajuste si todavía no ha llegado el cumpleaños este año
        if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNacimiento.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }
        return edad;
    }

    private void guardarTodo() {
        String nombre = etNombrePaciente.getText().toString().trim();
        String apellidoP = etApellidoPaterno.getText().toString().trim();
        String apellidoM = etApellidoMaterno.getText().toString().trim();
        String pesoStr = etPeso.getText().toString().trim();

        // --- Validaciones de nombre y apellidos (solo letras) ---
        if (!ValidacionUtils.esSoloLetras(nombre)) {
            ValidacionUtils.marcarError(etNombrePaciente, "Solo se permiten letras");
            return;
        }
        if (!ValidacionUtils.esSoloLetras(apellidoP)) {
            ValidacionUtils.marcarError(etApellidoPaterno, "Solo se permiten letras");
            return;
        }
        if (!ValidacionUtils.esSoloLetras(apellidoM)) {
            ValidacionUtils.marcarError(etApellidoMaterno, "Solo se permiten letras");
            return;
        }

        // --- Validación de peso (0.5 a 400 kg) ---
        if (!ValidacionUtils.esPesoValido(pesoStr, 0.5, 400)) {
            ValidacionUtils.marcarError(etPeso, "Peso inválido (0.5-400 kg)");
            return;
        }

        // --- Fecha de nacimiento ---
        if (fechaNacimientoSeleccionada.isEmpty()) {
            Toast.makeText(this, "Selecciona la fecha de nacimiento", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidacionUtils.fechaNoEsFutura(fechaNacimientoSeleccionada)) {
            Toast.makeText(this, "La fecha de nacimiento no puede ser futura", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- Validación de edad calculada (0 a 120 años) ---
        if (edadCalculada < 0 || edadCalculada > 120) {
            Toast.makeText(this, "La edad calculada está fuera de rango (0-120)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (listaDoctores.isEmpty()) {
            Toast.makeText(this, "No hay doctores registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- Datos de la consulta (Fragment) ---
        String motivo = consultaFragment.getMotivo();
        if (!ValidacionUtils.noVacio(motivo)) {
            Toast.makeText(this, "Escribe el motivo de la consulta", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!consultaFragment.isHoraSalidaSeleccionada()) {
            Toast.makeText(this, "Selecciona la hora de salida", Toast.LENGTH_SHORT).show();
            return;
        }

        double peso = Double.parseDouble(pesoStr);
        int edad = edadCalculada;

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

        // 2. Guardar consulta
        Consulta consulta = new Consulta(0,
                "Ninguna registrada",
                motivo,
                "Pendiente de diagnóstico",
                ahora,
                (int) idPacienteGenerado,
                doctorSeleccionado.getIdDoctor());
        consultaDAO.insertarConsulta(consulta);

        // 3. Guardar egreso
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