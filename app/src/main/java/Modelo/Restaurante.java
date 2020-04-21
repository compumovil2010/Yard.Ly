package Modelo;

import android.net.Uri;

public class Restaurante {

    public  static  final String PATH_REST = "rest/";
    private String nombre,direccion;
    private String horaapertura;
    private String horaclausura;
    private Uri foto;
    public Restaurante(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
