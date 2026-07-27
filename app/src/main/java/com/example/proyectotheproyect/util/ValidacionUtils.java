package com.example.proyectotheproyect.util;

import android.widget.EditText;

import java.util.regex.Pattern;

public class ValidacionUtils {

    // Solo letras (con acentos y ñ) y espacios
    private static final Pattern PATRON_SOLO_LETRAS =
            Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");

    // Solo dígitos
    private static final Pattern PATRON_SOLO_NUMEROS =
            Pattern.compile("^[0-9]+$");

    /**
     * Verdadero si el texto solo contiene letras y espacios (nombres, apellidos).
     */
    public static boolean esSoloLetras(String texto) {
        return texto != null && !texto.trim().isEmpty()
                && PATRON_SOLO_LETRAS.matcher(texto.trim()).matches();
    }

    /**
     * Verdadero si el texto es un número entero válido dentro de un rango.
     */
    public static boolean esEdadValida(String texto, int min, int max) {
        if (texto == null || texto.trim().isEmpty()) return false;
        if (!PATRON_SOLO_NUMEROS.matcher(texto.trim()).matches()) return false;
        try {
            int valor = Integer.parseInt(texto.trim());
            return valor >= min && valor <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verdadero si el texto es un número decimal positivo válido dentro de un rango.
     */
    public static boolean esPesoValido(String texto, double min, double max) {
        if (texto == null || texto.trim().isEmpty()) return false;
        try {
            double valor = Double.parseDouble(texto.trim());
            return valor >= min && valor <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verdadero si el texto no está vacío (para campos libres como "motivo").
     */
    public static boolean noVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Marca un EditText con mensaje de error visual (el ícono rojo estándar de Android).
     */
    public static void marcarError(EditText campo, String mensaje) {
        campo.setError(mensaje);
        campo.requestFocus();
    }
    /**
     * Verdadero si la fecha (formato yyyy-MM-dd) no es futura respecto a hoy.
     */
    public static boolean fechaNoEsFutura(String fechaTexto) {
        if (fechaTexto == null || fechaTexto.trim().isEmpty()) return false;
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            sdf.setLenient(false);
            java.util.Date fecha = sdf.parse(fechaTexto.trim());
            java.util.Date hoy = new java.util.Date();
            return fecha != null && !fecha.after(hoy);
        } catch (java.text.ParseException e) {
            return false;
        }
    }
}