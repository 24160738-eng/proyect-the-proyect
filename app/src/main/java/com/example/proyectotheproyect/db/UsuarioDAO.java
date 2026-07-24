package com.example.proyectotheproyect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {

    private DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Valida las credenciales contra la tabla usuario.
     * @return true si usuario y password coinciden con un registro.
     */
    public boolean validarLogin(String usuario, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean valido = false;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USUARIO,
                new String[]{"id_usuario"},
                "usuario = ? AND password = ?",
                new String[]{usuario, password},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            valido = true;
        }
        cursor.close();
        db.close();
        return valido;
    }
}