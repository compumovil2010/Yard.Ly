package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Comentarios extends AppCompatActivity {
TextView productoComentario;
ListView mlista;
List<String> mProjection= new ArrayList<>();
Product producto = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        productoComentario = findViewById(R.id.ProductoComentarios);
        producto = (Product) Objects.requireNonNull(getIntent().getBundleExtra("productoBundle")).getSerializable("producto");
        if (producto != null)
        productoComentario.setText(producto.getNomProducto());
        mlista=findViewById(R.id.opiniones);
        mProjection.add("4");
        mProjection.add("Opinion");
        mProjection.add("5");
        mProjection.add("Opinion");
        mProjection.add("6");
        mProjection.add("Opinion");
        mProjection.add("7");
        mProjection.add("Opinion");
        OpinionesAdapter opAdapter = new OpinionesAdapter(this, R.layout.opiniones,mProjection);
        mlista.setAdapter(opAdapter);
    }
}
