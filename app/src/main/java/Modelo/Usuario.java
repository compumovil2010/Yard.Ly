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
    private String apellido;
    private ArrayList<String > CarritoCompras;
    private Uri fotoPerfil;
    private float kmRecorridos;

    public Usuario() {
    }

    public Usuario(String mail, String nombre, String apellido, Uri fotoPerfil) {
        this.mail = mail;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fotoPerfil = fotoPerfil;
        kmRecorridos=0;
    }

    public String getMail() {
        return mail;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
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

    public void setApellido(String apellido) {
        this.apellido = apellido;
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
