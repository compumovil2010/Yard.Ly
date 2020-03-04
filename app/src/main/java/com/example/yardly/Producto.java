package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Producto extends AppCompatActivity {
    List<String> mProjection= new ArrayList<>();
    ListView mlista;
    private String nombre;
    private int precio;
    private String Descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        mlista=findViewById(R.id.opiniones);
        mProjection.add("Asunto");
        mProjection.add("Opinion");
        mProjection.add("Asunto");
        mProjection.add("Opinion");
        mProjection.add("Asunto");
        mProjection.add("Opinion");
        mProjection.add("Asunto");
        mProjection.add("Opinion");
        OpinionesAdapter opAdapter = new OpinionesAdapter(this, R.layout.opiniones,mProjection);
        mlista.setAdapter(opAdapter);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

}
