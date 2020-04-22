package Modelo;

import androidx.annotation.IntegerRes;

import java.util.ArrayList;

public class Pedido {

    public  static  final String PATH_PEDIDO = "pedido/";
    private ArrayList<String> productos;
    private ArrayList<Integer> cantprod;
    private String UsuPedido;
    private String domi;
    private String DirUsu;
    private String Empresa;
    private String Comentarios;
    private boolean Finalizado;
    public Pedido()
    {

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
}
