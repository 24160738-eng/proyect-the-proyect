package com.example.proyectotheproyect.modelo;

public class Receta {
    private int idReceta, idConsulta;
    private String fechaEmision, indicacionesGenerales;

    public Receta() {}

    public Receta(int idReceta, String fechaEmision, String indicacionesGenerales, int idConsulta) {
        this.idReceta = idReceta;
        this.fechaEmision = fechaEmision;
        this.indicacionesGenerales = indicacionesGenerales;
        this.idConsulta = idConsulta;
    }

    public int getIdReceta() { return idReceta; }
    public void setIdReceta(int idReceta) { this.idReceta = idReceta; }
    public String getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }
    public String getIndicacionesGenerales() { return indicacionesGenerales; }
    public void setIndicacionesGenerales(String indicacionesGenerales) { this.indicacionesGenerales = indicacionesGenerales; }
    public int getIdConsulta() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta = idConsulta; }
}