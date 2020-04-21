package Modelo;

import java.util.ArrayList;

public class Ropa extends Producto {

    public static final String PATH_ROPA="ropa/";
    private ArrayList<String> colores;
    private ArrayList<String> tallas;
    private String material;

    public Ropa() {
    }

    public ArrayList<String> getColores() {
        return colores;
    }

    public void setColores(ArrayList<String> colores) {
        this.colores = colores;
    }

    public ArrayList<String> getTallas() {
        return tallas;
    }

    public void setTallas(ArrayList<String> tallas) {
        this.tallas = tallas;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
