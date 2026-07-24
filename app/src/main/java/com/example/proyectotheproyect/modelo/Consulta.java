package com.example.proyectotheproyect.modelo;

public class Consulta {
    private int idConsulta, idPaciente, idDoctor;
    private String alergias, observacionesSintomas, diagnostico, fechaRegistro;

    public Consulta() {}

    public Consulta(int idConsulta, String alergias, String observacionesSintomas,
                    String diagnostico, String fechaRegistro, int idPaciente, int idDoctor) {
        this.idConsulta = idConsulta;
        this.alergias = alergias;
        this.observacionesSintomas = observacionesSintomas;
        this.diagnostico = diagnostico;
        this.fechaRegistro = fechaRegistro;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
    }

    public int getIdConsulta() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta = idConsulta; }
    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public String getObservacionesSintomas() { return observacionesSintomas; }
    public void setObservacionesSintomas(String observacionesSintomas) { this.observacionesSintomas = observacionesSintomas; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public int getIdDoctor() { return idDoctor; }
    public void setIdDoctor(int idDoctor) { this.idDoctor = idDoctor; }
}