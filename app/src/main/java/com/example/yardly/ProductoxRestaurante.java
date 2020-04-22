package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.AdapterView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductoxRestaurante extends AppCompatActivity {
    FloatingActionButton carrito;
    RecyclerView listProductsxrestaurant;
    Button btn_agregar;
    Product produc;
    private List<Product> productosxrestaurant = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productox_restaurante);
        carrito = findViewById(R.id.carritoProductxRest);
        btn_agregar = findViewById(R.id.btn_agregar);
        listProductsxrestaurant = findViewById(R.id.listProductosxResta);
        listProductsxrestaurant.setHasFixedSize( true );
        LinearLayoutManager ll = new LinearLayoutManager( this );//Si no funciona, quitar el v.
        listProductsxrestaurant.setLayoutManager( ll );
        crearProductosFalsos();
        initializeAdapter();
        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(v.getContext(), CarritoCompras.class);
                startActivity(inte);
            }
        });
    }
    private void crearProductosFalsos() {
        for( int i = 0 ; i < 7 ; i++ )
        {
            Product p = new Product("Ramen","34000","ala, muy sabroso","ItachiRamen con Sabor");
            productosxrestaurant.add( p );
        }
    }
    private void initializeAdapter() {
        ProductAdapter pAdapter = new ProductAdapter( productosxrestaurant );
        listProductsxrestaurant.setAdapter(pAdapter);
    }
}
