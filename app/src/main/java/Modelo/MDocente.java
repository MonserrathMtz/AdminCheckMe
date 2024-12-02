package Modelo;

import java.io.Serializable;

public class MDocente implements Serializable {

    private int idDocente;
    private String numTrabajador;
    private String nombre;
    private String app;
    private String apm;
    private String correo;
    private String grado;
    private String titulo;
    private int genero;
    private String contrasenia;
    private int rol;


    public MDocente() {
    }

    public MDocente(int idDocente, String numTrabajador, String nombre, String app, String apm, String correo, String grado, String titulo, int genero, String contrasenia, int rol) {
        this.idDocente = idDocente;
        this.numTrabajador = numTrabajador;
        this.nombre = nombre;
        this.app = app;
        this.apm = apm;
        this.correo = correo;
        this.grado = grado;
        this.titulo = titulo;
        this.genero = genero;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getNumTrabajador() {
        return numTrabajador;
    }

    public void setNumTrabajador(String numTrabajador) {
        this.numTrabajador = numTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apm) {
        this.apm = apm;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getGenero() {
        return genero;
    }
    public void setGenero(int genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "MDocente{" +
                "idDocente=" + idDocente +
                ", numTrabajador='" + numTrabajador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", app='" + app + '\'' +
                ", apm='" + apm + '\'' +
                ", correo='" + correo + '\'' +
                ", grado='" + grado + '\'' +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                '}';
    }
}
