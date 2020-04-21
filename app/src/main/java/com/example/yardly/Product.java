package com.example.yardly;

public class Product {

    private String nomProducto;
    private String precio;
    private String descripcion;
    private String nomEstab;

    public Product()
    {

    }

    public Product(String pnomProducto, String pprecio, String pdescripcion, String pnomEstab)
    {
        this.nomProducto = pnomProducto;
        this.precio = pprecio;
        this.descripcion = pdescripcion;
        this.nomEstab = pnomEstab;
    }


    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNomEstab() {
        return nomEstab;
    }

    public void setNomEstab(String nomEstab) {
        this.nomEstab = nomEstab;
    }
}
