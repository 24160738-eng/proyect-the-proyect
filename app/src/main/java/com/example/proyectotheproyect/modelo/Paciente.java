package com.example.proyectotheproyect.modelo;

public class Paciente {
    private int idPaciente;
    private String nombre, apellidoP, apellidoM, fechaNacimiento, genero, fechaHoraIngreso, creadoEn;
    private int edad;
    private double peso;

    public Paciente() {}

    public Paciente(int idPaciente, String nombre, String apellidoP, String apellidoM,
                    String fechaNacimiento, int edad, String genero, double peso,
                    String fechaHoraIngreso, String creadoEn) {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.genero = genero;
        this.peso = peso;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.creadoEn = creadoEn;
    }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidoP() { return apellidoP; }
    public void setApellidoP(String apellidoP) { this.apellidoP = apellidoP; }
    public String getApellidoM() { return apellidoM; }
    public void setApellidoM(String apellidoM) { this.apellidoM = apellidoM; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public String getFechaHoraIngreso() { return fechaHoraIngreso; }
    public void setFechaHoraIngreso(String fechaHoraIngreso) { this.fechaHoraIngreso = fechaHoraIngreso; }
    public String getCreadoEn() { return creadoEn; }
    public void setCreadoEn(String creadoEn) { this.creadoEn = creadoEn; }

    @Override
    public String toString() {
        return nombre + " " + apellidoP + " " + apellidoM;
    }
}