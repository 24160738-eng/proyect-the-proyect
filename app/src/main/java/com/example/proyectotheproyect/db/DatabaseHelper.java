package com.example.proyectotheproyect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hospital.db";
    private static final int DB_VERSION = 1;

    // Nombres de tablas
    public static final String TABLE_PACIENTES = "pacientes";
    public static final String TABLE_DOCTOR = "doctor";
    public static final String TABLE_CONSULTAS = "consultas";
    public static final String TABLE_EGRESOS = "egresos";
    public static final String TABLE_RECETAS = "recetas";
    public static final String TABLE_DETALLE_RECETA = "detalle_receta";
    public static final String TABLE_USUARIO = "usuario";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_PACIENTES + " (" +
                "id_paciente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "apellidop TEXT NOT NULL, " +
                "apellidom TEXT NOT NULL, " +
                "fecha_nacimiento TEXT NOT NULL, " +
                "edad INTEGER NOT NULL, " +
                "genero TEXT NOT NULL, " +
                "peso REAL NOT NULL, " +
                "fecha_hora_ingreso TEXT NOT NULL, " +
                "creado_en TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_DOCTOR + " (" +
                "id_doctor INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre_doctor TEXT NOT NULL, " +
                "num_cedula TEXT NOT NULL, " +
                "apellidop TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_CONSULTAS + " (" +
                "id_consulta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "alergias TEXT NOT NULL, " +
                "observaciones_sintomas TEXT NOT NULL, " +
                "diagnostico TEXT NOT NULL, " +
                "fecha_registro TEXT NOT NULL, " +
                "id_paciente INTEGER NOT NULL, " +
                "id_doctor INTEGER NOT NULL, " +
                "FOREIGN KEY(id_paciente) REFERENCES " + TABLE_PACIENTES + "(id_paciente), " +
                "FOREIGN KEY(id_doctor) REFERENCES " + TABLE_DOCTOR + "(id_doctor))");

        db.execSQL("CREATE TABLE " + TABLE_EGRESOS + " (" +
                "id_egreso INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "observaciones_egreso TEXT NOT NULL, " +
                "fecha_registro TEXT NOT NULL, " +
                "id_paciente INTEGER NOT NULL, " +
                "hora_salida TEXT NOT NULL, " +
                "FOREIGN KEY(id_paciente) REFERENCES " + TABLE_PACIENTES + "(id_paciente))");

        db.execSQL("CREATE TABLE " + TABLE_RECETAS + " (" +
                "id_receta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha_emision TEXT NOT NULL, " +
                "indicaciones_generales TEXT NOT NULL, " +
                "id_consulta INTEGER NOT NULL, " +
                "FOREIGN KEY(id_consulta) REFERENCES " + TABLE_CONSULTAS + "(id_consulta))");

        db.execSQL("CREATE TABLE " + TABLE_DETALLE_RECETA + " (" +
                "id_receta INTEGER NOT NULL, " +
                "medicamento_nombre TEXT NOT NULL, " +
                "dosis TEXT NOT NULL, " +
                "frecuencia TEXT NOT NULL, " +
                "via_administracion TEXT NOT NULL, " +
                "duracion TEXT, " +
                "FOREIGN KEY(id_receta) REFERENCES " + TABLE_RECETAS + "(id_receta))");

        db.execSQL("CREATE TABLE " + TABLE_USUARIO + " (" +
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL)");

        insertarDatosIniciales(db);
    }

    private void insertarDatosIniciales(SQLiteDatabase db) {

        // ---- Doctores (4) ----
        String[][] doctores = {
                {"Juan", "12345", "Ramirez"},
                {"Maria", "23456", "Lopez"},
                {"Carlos", "34567", "Hernandez"},
                {"Ana", "45678", "Martinez"}
        };
        for (String[] d : doctores) {
            ContentValues cv = new ContentValues();
            cv.put("nombre_doctor", d[0]);
            cv.put("num_cedula", d[1]);
            cv.put("apellidop", d[2]);
            db.insert(TABLE_DOCTOR, null, cv);
        }

        // ---- Pacientes (4) ----
        String[][] pacientes = {
                {"Pedro", "Gomez", "Sanchez", "1990-05-10", "36", "M", "78.5"},
                {"Laura", "Diaz", "Cruz", "1985-11-22", "40", "F", "65.2"},
                {"Miguel", "Torres", "Reyes", "2000-02-15", "26", "M", "82.0"},
                {"Sofia", "Vega", "Morales", "1995-08-30", "30", "F", "58.7"}
        };
        String ahora = "2026-07-23 10:00:00";
        for (String[] p : pacientes) {
            ContentValues cv = new ContentValues();
            cv.put("nombre", p[0]);
            cv.put("apellidop", p[1]);
            cv.put("apellidom", p[2]);
            cv.put("fecha_nacimiento", p[3]);
            cv.put("edad", Integer.parseInt(p[4]));
            cv.put("genero", p[5]);
            cv.put("peso", Double.parseDouble(p[6]));
            cv.put("fecha_hora_ingreso", ahora);
            cv.put("creado_en", ahora);
            db.insert(TABLE_PACIENTES, null, cv);
        }

        // ---- Consultas (4) ----
        for (int i = 1; i <= 4; i++) {
            ContentValues cv = new ContentValues();
            cv.put("alergias", "Ninguna");
            cv.put("observaciones_sintomas", "Dolor leve");
            cv.put("diagnostico", "Diagnostico de prueba " + i);
            cv.put("fecha_registro", ahora);
            cv.put("id_paciente", i);
            cv.put("id_doctor", i);
            db.insert(TABLE_CONSULTAS, null, cv);
        }

        // ---- Egresos (4) ----
        for (int i = 1; i <= 4; i++) {
            ContentValues cv = new ContentValues();
            cv.put("observaciones_egreso", "Alta medica");
            cv.put("fecha_registro", ahora);
            cv.put("id_paciente", i);
            cv.put("hora_salida", "14:30:00");
            db.insert(TABLE_EGRESOS, null, cv);
        }

        // ---- Recetas (4) ----
        for (int i = 1; i <= 4; i++) {
            ContentValues cv = new ContentValues();
            cv.put("fecha_emision", ahora);
            cv.put("indicaciones_generales", "Tomar con alimentos");
            cv.put("id_consulta", i);
            db.insert(TABLE_RECETAS, null, cv);
        }

        // ---- Detalle receta (4) ----
        String[][] detalles = {
                {"Paracetamol", "500mg", "Cada 8 horas", "Oral"},
                {"Ibuprofeno", "400mg", "Cada 12 horas", "Oral"},
                {"Amoxicilina", "250mg", "Cada 8 horas", "Oral"},
                {"Loratadina", "10mg", "Cada 24 horas", "Oral"}
        };
        for (int i = 0; i < detalles.length; i++) {
            ContentValues cv = new ContentValues();
            cv.put("id_receta", i + 1);
            cv.put("medicamento_nombre", detalles[i][0]);
            cv.put("dosis", detalles[i][1]);
            cv.put("frecuencia", detalles[i][2]);
            cv.put("via_administracion", detalles[i][3]);
            cv.put("duracion", "7 dias");
            db.insert(TABLE_DETALLE_RECETA, null, cv);
        }

        // ---- Usuarios para login (4) ----
        String[][] usuarios = {
                {"admin", "admin123"},
                {"doctor1", "doc123"},
                {"recepcion", "rec123"},
                {"qwerty", "1234"}
        };
        for (String[] u : usuarios) {
            ContentValues cv = new ContentValues();
            cv.put("usuario", u[0]);
            cv.put("password", u[1]);
            db.insert(TABLE_USUARIO, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE_RECETA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EGRESOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSULTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }
}