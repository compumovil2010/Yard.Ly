package com.example.yardly;

public class LugarEntrega {

private String Direccion;
private double longitud;
private double latitud;
private String InfoAdicional;

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getInfoAdicional() {
        return InfoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        InfoAdicional = infoAdicional;
    }
}
