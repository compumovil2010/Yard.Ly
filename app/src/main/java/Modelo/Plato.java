package Modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Plato extends Producto {
    public static final String PATH_PLATO="plato/";
    private int cantidadpersonas;
    private ArrayList<String> ingredientes;

    public Plato() {
    }

    public int getCantidadpersonas() {
        return cantidadpersonas;
    }

    public void setCantidadpersonas(int cantidadpersonas) {
        this.cantidadpersonas = cantidadpersonas;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
