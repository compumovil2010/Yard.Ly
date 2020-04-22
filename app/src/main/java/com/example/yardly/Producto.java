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

public class Producto extends AppCompatActivity {
    private String nombre;
    private int precio =0;
    private String Descripcion;
    private boolean habilitado;
    //tipo resena
    private List<String> resenas;
    private List<String> tags;
    private int cantidadNum;
    private TextView nombreTextView, descripcionTextView, precioTextView, cantidad, total, storeName;
    private Button mas, menos, comentarios;
    Product pro;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        pro = (Product) Objects.requireNonNull(getIntent().getBundleExtra("Bproducto")).getSerializable("producto");
        pro = new Product("Pizza de vegetales",String.valueOf(10000),"esta es la ldescripcioooooooooooooooon","Sitio vegetariano");
        nombreTextView = findViewById(R.id.nomProduct);
        descripcionTextView = findViewById(R.id.descripProduc);
        precioTextView = findViewById(R.id.precioProduct);
        storeName = findViewById(R.id.storeName);
        cantidad = findViewById(R.id.cantProduct);
        total = findViewById(R.id.total);
        mas = findViewById(R.id.mas);
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidadNum = Integer.parseInt(cantidad.getText().toString());
                cantidadNum = cantidadNum + 1;
                precio  = Integer.parseInt(precioTextView.getText().toString());
                cantidad.setText(String.valueOf(cantidadNum));
                total.setText(String.valueOf(precio*cantidadNum));
            }
        });
        menos = findViewById(R.id.menos);
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidadNum = Integer.parseInt(cantidad.getText().toString());
                if(cantidadNum>1){
                    precio  = Integer.parseInt(precioTextView.getText().toString());
                    cantidadNum = cantidadNum - 1;
                    cantidad.setText(String.valueOf(cantidadNum));
                    total.setText(String.valueOf(precio*cantidadNum));
                }
            }
        });
        comentarios = findViewById(R.id.comentariosProd);
        comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),Comentarios.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("producto", pro);
                intent.putExtra("productoBundle",bundle);
                startActivity(intent);
            }
        });
        if (pro!=null){
            nombreTextView.setText(pro.getNomProducto());
            descripcionTextView.setText(pro.getDescripcion());
            precioTextView.setText(String.valueOf(pro.getPrecio()));
            storeName.setText(pro.getNomEstab());
        }
        b=findViewById(R.id.aggCarrit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),CarritoCompras.class);
                startActivity(registro);
            }
        });
        precio  = Integer.parseInt(precioTextView.getText().toString());
        int cant = Integer.parseInt(cantidad.getText().toString());
        total.setText(String.valueOf(precio*cant));
    }

}