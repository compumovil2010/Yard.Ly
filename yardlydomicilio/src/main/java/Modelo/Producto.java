package Modelo;

import java.util.ArrayList;

public class Producto {

    public  static  final String PATH_PROD = "prod/";
    public  static  final String PATH_PRODIMG = "prod/img/";
    private String descrip,nombre;
    private boolean habilitado;
    private ArrayList<String> tags;
    private double precio;
    public Producto()
    {

    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
