package com.domicilio.yardly;

import java.io.Serializable;
import java.util.ArrayList;

public class Pedido implements Serializable {
    public  static  final String PATH_PEDIDO = "pedido/";
    private String Fecha;
    private String NombreProducto;
    private float Precio;
    private ArrayList<String> productos;
    private ArrayList<Integer> cantprod;
    private String UsuPedido;
    private String idChat;
    private String domi;
    private String DirUsu;
    private String Empresa;
    private String Comentarios;

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public Pedido()
    {

    }
    public Pedido(String fecha, String nomprod, float precio, ArrayList<String> pproductos, ArrayList<Integer> cantp, String usup, String dom, String dirusr, String emp, String coments)
    {
        this.Fecha = fecha;
        this.NombreProducto = nomprod;
        this.Precio = precio;
        this.productos = pproductos;
        this.cantprod = cantp;
        this.UsuPedido = usup;
        this.domi = dom;
        this.DirUsu = dirusr;
        this.Empresa = emp;
        this.Comentarios = coments;

    }


    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        NombreProducto = nombreProducto;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<String> productos) {
        this.productos = productos;
    }

    public String getUsuPedido() {
        return UsuPedido;
    }

    public void setUsuPedido(String usuPedido) {
        UsuPedido = usuPedido;
    }

    public ArrayList<Integer> getCantprod() {
        return cantprod;
    }

    public void setCantprod(ArrayList<Integer> cantprod) {
        this.cantprod = cantprod;
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

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String comentarios) {
        Comentarios = comentarios;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa;
    }
}

