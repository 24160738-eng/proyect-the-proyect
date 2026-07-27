package com.example.proyectotheproyect;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicaService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.musica_menu);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.5f, 0.5f); // volumen al 50%, ajusta si quieres
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Si ya está sonando, no la reinicia (evita el corte al volver a llamar el servicio)
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // no usamos binding, solo start/stop
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}