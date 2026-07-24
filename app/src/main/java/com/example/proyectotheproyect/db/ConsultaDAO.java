package com.example.proyectotheproyect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectotheproyect.modelo.Consulta;

public class ConsultaDAO {

    private DatabaseHelper dbHelper;

    public ConsultaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertarConsulta(Consulta c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("alergias", c.getAlergias());
        cv.put("observaciones_sintomas", c.getObservacionesSintomas());
        cv.put("diagnostico", c.getDiagnostico());
        cv.put("fecha_registro", c.getFechaRegistro());
        cv.put("id_paciente", c.getIdPaciente());
        cv.put("id_doctor", c.getIdDoctor());

        long id = db.insert(DatabaseHelper.TABLE_CONSULTAS, null, cv);
        db.close();
        return id;
    }
}