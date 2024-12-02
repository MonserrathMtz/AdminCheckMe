package Modelo;

import java.io.Serializable;

public class MPeriodo implements Serializable {
private int idPeriodo;
private String nombre;
private String fechaI;
private String fechaF;

    @Override
    public String toString() {
        return "MPeriodo{" +
                "idPeriodo=" + idPeriodo +
                ", nombre='" + nombre + '\'' +
                ", fechaI='" + fechaI + '\'' +
                ", fechaF='" + fechaF + '\'' +
                '}';
    }

    public MPeriodo(int idPeriodo, String nombre, String fechaI, String fechaF) {
        this.idPeriodo = idPeriodo;
        this.nombre = nombre;
        this.fechaI = fechaI;
        this.fechaF = fechaF;
    }

    public MPeriodo() {
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaI() {
        return fechaI;
    }

    public void setFechaI(String fechaI) {
        this.fechaI = fechaI;
    }

    public String getFechaF() {
        return fechaF;
    }

    public void setFechaF(String fechaF) {
        this.fechaF = fechaF;
    }
}
