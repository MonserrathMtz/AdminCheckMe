package Modelo;

import java.io.Serializable;

public class MEstudiante implements Serializable {
    private String nombre;
    private int idInscripcion;
    private String app;
    private String apm;
    private String correo;
    private String matricula;
    private int idGrupo;
    private String nombreCorto;
    private int idEstudiante;
    private int op;
    private String edo;
    private String muni;
    private String col;
    private String gen;
    private String contrasenia;
    private int idCarrera;

    public MEstudiante() {
    }

    public MEstudiante(String nombre, int idInscripcion, String app, String apm, String correo, String matricula, int idGrupo, String nombreCorto, int idEstudiante, int op, String edo, String muni, String col, String gen, String contrasenia, int idCarrera) {
        this.nombre = nombre;
        this.idInscripcion = idInscripcion;
        this.app = app;
        this.apm = apm;
        this.correo = correo;
        this.matricula = matricula;
        this.idGrupo = idGrupo;
        this.nombreCorto = nombreCorto;
        this.idEstudiante = idEstudiante;
        this.op = op;
        this.edo = edo;
        this.muni = muni;
        this.col = col;
        this.gen = gen;
        this.contrasenia = contrasenia;
        this.idCarrera = idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public String getEdo() {
        return edo;
    }

    public void setEdo(String edo) {
        this.edo = edo;
    }

    public String getMuni() {
        return muni;
    }

    public void setMuni(String muni) {
        this.muni = muni;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    @Override
    public String toString() {
        return "MEstudiante{" +
                "nombre='" + nombre + '\'' +
                ", idInscripcion=" + idInscripcion +
                ", app='" + app + '\'' +
                ", apm='" + apm + '\'' +
                ", correo='" + correo + '\'' +
                ", matricula='" + matricula + '\'' +
                ", idGrupo=" + idGrupo +
                ", nombreCorto='" + nombreCorto + '\'' +
                ", idEstudiante=" + idEstudiante +
                ", op=" + op +
                ", edo='" + edo + '\'' +
                ", muni='" + muni + '\'' +
                ", col='" + col + '\'' +
                ", gen='" + gen + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", idCarrera=" + idCarrera +
                '}';
    }
}