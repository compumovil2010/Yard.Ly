package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Producto extends AppCompatActivity implements Serializable {
    private String nombre;
    private int precio;
    private String Descripcion;
    private boolean habilitado;
    //tipo resena
    private List<String> resenas;
    private List<String> tags;

    List<String> mProjection= new ArrayList<>();
    ListView mlista;
    private int cantidadNum;
    private TextView nombreTextView, descripcionTextView, precioTextView, cantidad, total;
    private Button mas, menos;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        Producto pro = (Producto) Objects.requireNonNull(getIntent().getBundleExtra("Bproducto")).getSerializable("producto");
        nombreTextView = findViewById(R.id.nomProduct);
        descripcionTextView = findViewById(R.id.descripProduc);
        precioTextView = findViewById(R.id.precioProduct);
        cantidad = findViewById(R.id.cantProduct);
        total = findViewById(R.id.total);
        mas = findViewById(R.id.mas);
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidadNum = Integer.parseInt(cantidad.getText().toString());
                cantidadNum = cantidadNum + 1;
                cantidad.setText(String.valueOf(cantidadNum));
            }
        });
        menos = findViewById(R.id.menos);
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidadNum = Integer.parseInt(cantidad.getText().toString());
                if(cantidadNum>1){
                    cantidadNum = cantidadNum - 1;
                    cantidad.setText(String.valueOf(cantidadNum));
                }
            }
        });
        if (pro!=null){
            nombreTextView.setText(pro.getNombre());
            descripcionTextView.setText(pro.getDescripcion());
            //precioTextView.setText(pro.getPrecio());
        }
        b=findViewById(R.id.aggCarrit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),CarritoCompras.class);
                startActivity(registro);
            }
        });
        //total.setText(Integer.parseInt(precioTextView.getText().toString())*Integer.parseInt(cantidad.getText().toString()));
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
