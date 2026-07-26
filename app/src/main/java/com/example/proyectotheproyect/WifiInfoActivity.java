package com.example.proyectotheproyect;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WifiInfoActivity extends AppCompatActivity {

    private static final int CODIGO_PERMISO_UBICACION = 100;

    private TextView tvSsid, tvBssid, tvIp, tvVelocidad, tvSenal, tvFrecuencia;
    private Button btnRefrescarWifi, btnVolverMenu;

    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wifi_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvSsid = findViewById(R.id.tvSsid);
        tvBssid = findViewById(R.id.tvBssid);
        tvIp = findViewById(R.id.tvIp);
        tvVelocidad = findViewById(R.id.tvVelocidad);
        tvSenal = findViewById(R.id.tvSenal);
        tvFrecuencia = findViewById(R.id.tvFrecuencia);
        btnRefrescarWifi = findViewById(R.id.btnRefrescarWifi);
        btnVolverMenu = findViewById(R.id.btnVolverMenu);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        btnRefrescarWifi.setOnClickListener(v -> verificarPermisoYMostrarInfo());

        btnVolverMenu.setOnClickListener(v -> {
            startActivity(new Intent(WifiInfoActivity.this, MenuActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarPermisoYMostrarInfo();
    }

    private void verificarPermisoYMostrarInfo() {
        boolean tieneFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        boolean tieneCoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        if (tieneFine && tieneCoarse) {
            mostrarInfoWifi();
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
                mostrarInfoWifi();
            } else {
                Toast.makeText(this,
                        "Se necesita el permiso de ubicación para leer el SSID de la red",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void mostrarInfoWifi() {
        if (wifiManager == null) {
            Toast.makeText(this, "WifiManager no disponible en este dispositivo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "El WiFi está desactivado", Toast.LENGTH_SHORT).show();
            return;
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            Toast.makeText(this, "No se pudo obtener información de la red", Toast.LENGTH_SHORT).show();
            return;
        }

        String ssid = wifiInfo.getSSID();
        // El SSID a veces viene entre comillas dobles, se las quitamos
        if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }

        String bssid = wifiInfo.getBSSID();
        String ip = Formatter.formatIpAddress(wifiInfo.getIpAddress());
        int velocidad = wifiInfo.getLinkSpeed(); // Mbps
        int senal = wifiInfo.getRssi(); // dBm
        int frecuencia = wifiInfo.getFrequency(); // MHz

        tvSsid.setText(ssid != null ? ssid : "---");
        tvBssid.setText(bssid != null ? bssid : "---");
        tvIp.setText(ip);
        tvVelocidad.setText(velocidad + " Mbps");
        tvSenal.setText(senal + " dBm");
        tvFrecuencia.setText(frecuencia + " MHz");
    }
}