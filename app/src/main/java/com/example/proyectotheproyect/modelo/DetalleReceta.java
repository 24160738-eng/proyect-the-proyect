package com.example.proyectotheproyect.modelo;

public class DetalleReceta {
    private int idReceta;
    private String medicamentoNombre, dosis, frecuencia, viaAdministracion, duracion;

    public DetalleReceta() {}

    public DetalleReceta(int idReceta, String medicamentoNombre, String dosis,
                         String frecuencia, String viaAdministracion, String duracion) {
        this.idReceta = idReceta;
        this.medicamentoNombre = medicamentoNombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.viaAdministracion = viaAdministracion;
        this.duracion = duracion;
    }

    public int getIdReceta() { return idReceta; }
    public void setIdReceta(int idReceta) { this.idReceta = idReceta; }
    public String getMedicamentoNombre() { return medicamentoNombre; }
    public void setMedicamentoNombre(String medicamentoNombre) { this.medicamentoNombre = medicamentoNombre; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
    public String getViaAdministracion() { return viaAdministracion; }
    public void setViaAdministracion(String viaAdministracion) { this.viaAdministracion = viaAdministracion; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
}