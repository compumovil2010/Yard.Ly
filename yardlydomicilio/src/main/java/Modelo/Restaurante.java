package Modelo;

import android.net.Uri;

import java.io.Serializable;

public class Restaurante implements Serializable {

    public  static  final String PATH_REST = "restaurants/";
    private String nombreR,direccion;
    private String horaapertura;
    private String horaclausura;
    private String descripR;
    private Uri foto;
    public Restaurante(){
    }

    public String getNombreR() {
        return nombreR;
    }

    public void setNombreR(String nombreR) {
        this.nombreR = nombreR;
    }

    public String getDescripR() {
        return descripR;
    }

    public void setDescripR(String descripR) {
        this.descripR = descripR;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHoraapertura() {
        return horaapertura;
    }

    public void setHoraapertura(String horaapertura) {
        this.horaapertura = horaapertura;
    }

    public String getHoraclausura() {
        return horaclausura;
    }

    public void setHoraclausura(String horaclausura) {
        this.horaclausura = horaclausura;
    }

    public Uri getFoto() {
        return foto;
    }

    public void setFoto(Uri foto) {
        this.foto = foto;
    }
}
