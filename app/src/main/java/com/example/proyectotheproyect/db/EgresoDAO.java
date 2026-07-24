package com.example.proyectotheproyect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectotheproyect.modelo.Egreso;

public class EgresoDAO {

    private DatabaseHelper dbHelper;

    public EgresoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertarEgreso(Egreso e) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("observaciones_egreso", e.getObservacionesEgreso());
        cv.put("fecha_registro", e.getFechaRegistro());
        cv.put("id_paciente", e.getIdPaciente());
        cv.put("hora_salida", e.getHoraSalida());

        long id = db.insert(DatabaseHelper.TABLE_EGRESOS, null, cv);
        db.close();
        return id;
    }
}