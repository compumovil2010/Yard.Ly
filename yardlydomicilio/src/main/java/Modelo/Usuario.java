package Modelo;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;

public class Usuario {
    public  static  final String PATH_USERS = "users/";
    public static final String PATH_PORFILE_PHOTO = "users/porfile_photos";
    private String mail;
    private String nombre;
    private ArrayList<String > CarritoCompras;
    private Uri fotoPerfil;
    private float kmRecorridos;
    private ArrayList<String> pedidos;
    public Usuario() {
    }

    public Usuario(String mail, String nombre, Uri fotoPerfil) {
        this.mail = mail;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        kmRecorridos=0;
    }

    public ArrayList<String> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<String> pedidos) {
        this.pedidos = pedidos;
    }

    public String getMail() {
        return mail;
    }

    public String getNombre() {
        return nombre;
    }


    public Uri getFotoPerfil() {
        return fotoPerfil;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setFotoPerfil(Uri fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public float getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(float kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public ArrayList<String> getCarritoCompras() {
        return CarritoCompras;
    }

    public void setCarritoCompras(ArrayList<String> carritoCompras) {
        CarritoCompras = carritoCompras;
    }
}
