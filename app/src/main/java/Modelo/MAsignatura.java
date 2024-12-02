package Modelo;

import java.io.Serializable;

public class MAsignatura implements Serializable {
    private int idAsignatura;
    private String nombreCorto;
    private String nombreLargo;
    private String clave;
    private int idCarrera;

    public MAsignatura() {
    }

    public MAsignatura(int idAsignatura, String nombreCorto, String nombreLargo, String clave, int idCarrera) {
        this.idAsignatura = idAsignatura;
        this.nombreCorto = nombreCorto;
        this.nombreLargo = nombreLargo;
        this.clave = clave;
        this.idCarrera = idCarrera;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    @Override
    public String toString() {
        return "MAsignatura{" +
                "idAsignatura=" + idAsignatura +
                ", nombreCorto='" + nombreCorto + '\'' +
                ", nombreLargo='" + nombreLargo + '\'' +
                ", clave='" + clave + '\'' +
                ", idCarrera=" + idCarrera +
                '}';
    }
}
