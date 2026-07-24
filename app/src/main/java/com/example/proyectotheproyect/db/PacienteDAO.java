package com.example.proyectotheproyect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import com.example.proyectotheproyect.modelo.Paciente;
import com.example.proyectotheproyect.modelo.PacienteConDoctor;

public class PacienteDAO {

    private DatabaseHelper dbHelper;

    public PacienteDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<PacienteConDoctor> obtenerTodosConDoctor() {
        List<PacienteConDoctor> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT p.id_paciente, p.nombre, p.apellidop, p.apellidom, p.edad, p.genero, " +
                "(d.nombre_doctor || ' ' || d.apellidop) AS nombre_doctor " +
                "FROM pacientes p " +
                "LEFT JOIN consultas c ON c.id_paciente = p.id_paciente " +
                "LEFT JOIN doctor d ON d.id_doctor = c.id_doctor " +
                "ORDER BY p.id_paciente DESC";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                PacienteConDoctor p = new PacienteConDoctor(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_paciente")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        cursor.getString(cursor.getColumnIndexOrThrow("apellidop")),
                        cursor.getString(cursor.getColumnIndexOrThrow("apellidom")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                        cursor.getString(cursor.getColumnIndexOrThrow("genero")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre_doctor"))
                );
                lista.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    /**
     * Inserta un paciente y regresa el id generado, o -1 si falla.
     */
    public long insertarPaciente(Paciente p) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nombre", p.getNombre());
        cv.put("apellidop", p.getApellidoP());
        cv.put("apellidom", p.getApellidoM());
        cv.put("fecha_nacimiento", p.getFechaNacimiento());
        cv.put("edad", p.getEdad());
        cv.put("genero", p.getGenero());
        cv.put("peso", p.getPeso());
        cv.put("fecha_hora_ingreso", p.getFechaHoraIngreso());
        cv.put("creado_en", p.getCreadoEn());

        long id = db.insert(DatabaseHelper.TABLE_PACIENTES, null, cv);
        db.close();
        return id;
    }
}