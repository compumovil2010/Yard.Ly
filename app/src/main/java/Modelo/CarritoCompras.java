package Modelo;

import com.example.yardly.Carrito;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras {

    private List<String> productos;
    private List<Integer> cantprod;
    private String usrId;

    public CarritoCompras()
    {

    }
    public CarritoCompras(ArrayList<String>pproductos, ArrayList<Integer> pcantidadp, String pusrId)
    {
        this.productos = pproductos;
        this.cantprod = pcantidadp;
        this.usrId = pusrId;
    }

    public List<String> getProductos() {
        return productos;
    }

    public void setProductos(List<String> productos) {
        this.productos = productos;
    }

    public List<Integer> getCantprod() {
        return cantprod;
    }

    public void setCantprod(List<Integer> cantprod) {
        this.cantprod = cantprod;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}
