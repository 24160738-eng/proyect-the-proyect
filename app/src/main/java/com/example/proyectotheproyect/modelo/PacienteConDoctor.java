package com.example.proyectotheproyect.modelo;

public class PacienteConDoctor {

    private int idPaciente;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private int edad;
    private String genero;
    private String nombreDoctor; // puede venir null si no tiene consulta/doctor asignado

    public PacienteConDoctor(int idPaciente, String nombre, String apellidoP, String apellidoM,
                             int edad, String genero, String nombreDoctor) {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.edad = edad;
        this.genero = genero;
        this.nombreDoctor = nombreDoctor;
    }

    public int getIdPaciente() { return idPaciente; }
    public String getNombre() { return nombre; }
    public String getApellidoP() { return apellidoP; }
    public String getApellidoM() { return apellidoM; }
    public int getEdad() { return edad; }
    public String getGenero() { return genero; }
    public String getNombreDoctor() { return nombreDoctor; }
}