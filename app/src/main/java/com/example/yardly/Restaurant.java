package com.example.yardly;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {

    public  static  final String PATH_REST = "restaurants/";
    private String nombreR,direccion;
    private String horaapertura;
    private String horaclausura;
    private String descripR;
    private Uri foto;
    private ArrayList<String> pedidosSinD;
    private ArrayList<String> pedidosConD;
    private ArrayList<String> domiciliarios;
    public Restaurant()
    {

    }

    public Restaurant(String pnombreR, String pdescripR )
    {
        this.nombreR = pnombreR;
        this.descripR = pdescripR;
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

    public ArrayList<String> getPedidosSinD() {
        return pedidosSinD;
    }

    public void setPedidosSinD(ArrayList<String> pedidosSinD) {
        this.pedidosSinD = pedidosSinD;
    }

    public ArrayList<String> getPedidosConD() {
        return pedidosConD;
    }

    public void setPedidosConD(ArrayList<String> pedidosConD) {
        this.pedidosConD = pedidosConD;
    }

    public static String getPathRest() {
        return PATH_REST;
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

    public ArrayList<String> getDomiciliarios() {
        return domiciliarios;
    }

    public void setDomiciliarios(ArrayList<String> domiciliarios) {
        this.domiciliarios = domiciliarios;
    }
}
