package com.example.proyectotheproyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectotheproyect.db.UsuarioDAO;

public class LoginActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private EditText etUsuario, etPassword;
    private TextView tvErrorLogin;
    private Button btnLogin;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        tvErrorLogin = findViewById(R.id.tvErrorLogin);
        btnLogin = findViewById(R.id.btnLogin);

        usuarioDAO = new UsuarioDAO(this);

        btnLogin.setOnClickListener(v -> intentarLogin());
        mediaPlayer = MediaPlayer.create(this, R.raw.musica_login);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void intentarLogin() {
        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            tvErrorLogin.setText("Completa usuario y contraseña");
            tvErrorLogin.setVisibility(View.VISIBLE);
            return;
        }

        boolean valido = usuarioDAO.validarLogin(usuario, password);

        if (valido) {
            tvErrorLogin.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            tvErrorLogin.setText("Usuario o contraseña incorrectos");
            tvErrorLogin.setVisibility(View.VISIBLE);
        }
    }
}