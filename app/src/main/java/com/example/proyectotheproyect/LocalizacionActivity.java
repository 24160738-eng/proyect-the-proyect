package com.example.proyectotheproyect;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class LocalizacionActivity extends AppCompatActivity {

    private static final int CODIGO_PERMISO_UBICACION = 200;

    private TextView tvEstadoUbicacion, tvCoordX, tvCoordY, tvPrecision;
    private Button btnObtenerUbicacion;

    private FusedLocationProviderClient clienteUbicacion;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private boolean actualizacionesActivas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_localizacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvEstadoUbicacion = findViewById(R.id.tvEstadoUbicacion);
        tvCoordX = findViewById(R.id.tvCoordX);
        tvCoordY = findViewById(R.id.tvCoordY);
        tvPrecision = findViewById(R.id.tvPrecision);
        btnObtenerUbicacion = findViewById(R.id.btnObtenerUbicacion);

        clienteUbicacion = LocationServices.getFusedLocationProviderClient(this);

        // Cada cuánto queremos actualizaciones de ubicación (en milisegundos)
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000)
                .setMinUpdateIntervalMillis(2000)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location ubicacion = locationResult.getLastLocation();
                if (ubicacion != null) {
                    mostrarUbicacion(ubicacion);
                }
            }
        };

        btnObtenerUbicacion.setOnClickListener(v -> {
            if (actualizacionesActivas) {
                detenerActualizaciones();
            } else {
                verificarPermisoYEmpezar();
            }
        });
    }

    private void verificarPermisoYEmpezar() {
        boolean tieneFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        if (tieneFine) {
            iniciarActualizaciones();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    CODIGO_PERMISO_UBICACION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_PERMISO_UBICACION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarActualizaciones();
            } else {
                Toast.makeText(this, "Se necesita el permiso de ubicación", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void iniciarActualizaciones() {
        tvEstadoUbicacion.setText("Buscando ubicación...");
        clienteUbicacion.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
        actualizacionesActivas = true;
        btnObtenerUbicacion.setText("Detener ubicación");
    }

    private void detenerActualizaciones() {
        clienteUbicacion.removeLocationUpdates(locationCallback);
        actualizacionesActivas = false;
        btnObtenerUbicacion.setText("Obtener ubicación");
        tvEstadoUbicacion.setText("Ubicación detenida");
    }

    private void mostrarUbicacion(Location ubicacion) {
        tvEstadoUbicacion.setText("Ubicación activa");
        tvCoordX.setText(String.valueOf(ubicacion.getLatitude()));
        tvCoordY.setText(String.valueOf(ubicacion.getLongitude()));
        tvPrecision.setText(ubicacion.getAccuracy() + " m");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (actualizacionesActivas) {
            clienteUbicacion.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (actualizacionesActivas) {
            clienteUbicacion.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
        }
    }
}