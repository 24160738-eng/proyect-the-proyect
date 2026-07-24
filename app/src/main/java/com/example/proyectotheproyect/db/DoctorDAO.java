package com.example.proyectotheproyect.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectotheproyect.modelo.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    private DatabaseHelper dbHelper;

    public DoctorDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Doctor> obtenerTodos() {
        List<Doctor> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_DOCTOR,
                null, null, null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                Doctor d = new Doctor(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_doctor")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre_doctor")),
                        cursor.getString(cursor.getColumnIndexOrThrow("num_cedula")),
                        cursor.getString(cursor.getColumnIndexOrThrow("apellidop"))
                );
                lista.add(d);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }
}