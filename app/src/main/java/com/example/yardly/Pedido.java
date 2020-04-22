package com.example.yardly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Pedido implements Serializable {
    public  static  final String PATH_PEDIDO = "pedido/";
    private String Fecha;
    private String NombreProducto;
    private float Precio;
    private ArrayList<String> productos;
    private ArrayList<Integer> cantprod;
    private String UsuPedido;
    private String domi;
    private String DirUsu;
    private String Empresa;
    private String Comentarios;

    public Pedido()
    {

    }

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<String> productos) {
        this.productos = productos;
    }

    public ArrayList<Integer> getCantprod() { return cantprod;}

    public String getUsuPedido() {
        return UsuPedido;
    }

    public void setUsuPedido(String usuPedido) {
        UsuPedido = usuPedido;
    }

    public String getDomi() {
        return domi;
    }

    public void setDomi(String domi) {
        this.domi = domi;
    }

    public String getDirUsu() {
        return DirUsu;
    }

    public void setDirUsu(String dirUsu) {
        DirUsu = dirUsu;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa;
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String comentarios) {
        Comentarios = comentarios;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        NombreProducto = nombreProducto;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}

