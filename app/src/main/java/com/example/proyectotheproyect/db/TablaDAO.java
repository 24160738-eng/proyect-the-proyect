package com.example.proyectotheproyect.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TablaDAO {

    private DatabaseHelper dbHelper;

    public TablaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static class ResultadoTabla {
        public List<String> columnas = new ArrayList<>();
        public List<List<String>> filas = new ArrayList<>();
    }

    public ResultadoTabla obtenerContenido(String nombreTabla) {
        ResultadoTabla resultado = new ResultadoTabla();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(nombreTabla, null, null, null, null, null, null);

        // Guardamos los nombres reales de las columnas de esa tabla
        for (String nombreColumna : cursor.getColumnNames()) {
            resultado.columnas.add(nombreColumna);
        }

        if (cursor.moveToFirst()) {
            do {
                List<String> fila = new ArrayList<>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String valor = cursor.getString(i);
                    fila.add(valor != null ? valor : "");
                }
                resultado.filas.add(fila);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return resultado;
    }
}