package com.example.proyectotheproyect.modelo;

public class Egreso {
    private int idEgreso, idPaciente;
    private String observacionesEgreso, fechaRegistro, horaSalida;

    public Egreso() {}

    public Egreso(int idEgreso, String observacionesEgreso, String fechaRegistro,
                  int idPaciente, String horaSalida) {
        this.idEgreso = idEgreso;
        this.observacionesEgreso = observacionesEgreso;
        this.fechaRegistro = fechaRegistro;
        this.idPaciente = idPaciente;
        this.horaSalida = horaSalida;
    }

    public int getIdEgreso() { return idEgreso; }
    public void setIdEgreso(int idEgreso) { this.idEgreso = idEgreso; }
    public String getObservacionesEgreso() { return observacionesEgreso; }
    public void setObservacionesEgreso(String observacionesEgreso) { this.observacionesEgreso = observacionesEgreso; }
    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
}