package Modelo;

import java.io.Serializable;

public class MCarreras implements Serializable {
    private int idCarrera;
    private String nombreCorto;
    private String nombreLargo;
    private String clave;

    @Override
    public String toString() {
        return "MCarreras{" +
                "idCarrera=" + idCarrera +
                ", nombreCorto='" + nombreCorto + '\'' +
                ", nombreLargo='" + nombreLargo + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
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

    public MCarreras(int idCarrera, String nombreCorto, String nombreLargo, String clave) {
        this.idCarrera = idCarrera;
        this.nombreCorto = nombreCorto;
        this.nombreLargo = nombreLargo;
        this.clave = clave;
    }

    public MCarreras() {
    }
}
