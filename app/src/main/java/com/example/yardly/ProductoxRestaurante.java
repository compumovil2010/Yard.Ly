package com.example.yardly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.AdapterView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductoxRestaurante extends AppCompatActivity {
    FloatingActionButton carrito;
    RecyclerView listProductsxrestaurant;
    Button btn_agregar;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String nombre;
    TextView nombreRestaurant, productosxrestaurante;
    Product produc;
    public static final String PATH_PRODUCTS = "products/";
    public List< Product > products;
    private List<Product> productosxrestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productox_restaurante);
        database = FirebaseDatabase.getInstance();
        carrito = findViewById(R.id.carritoProductxRest);
        btn_agregar = findViewById(R.id.btn_agregar);
        nombreRestaurant = findViewById(R.id.restaurantNameProducxResta);
        productosxrestaurante =findViewById(R.id.productosxrestaurante);
        listProductsxrestaurant = findViewById(R.id.listProductosxResta);
        listProductsxrestaurant.setHasFixedSize( true );
        LinearLayoutManager ll = new LinearLayoutManager( this );//Si no funciona, quitar el v.
        listProductsxrestaurant.setLayoutManager( ll );
        nombre = getIntent().getStringExtra("nombreR");
        productosxrestaurant = new ArrayList<>();
        if (nombre != null){
            nombreRestaurant.setText(nombre);
            buscar();
        }
        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(v.getContext(), CarritoCompras.class);
                startActivity(inte);
            }
        });

    }
    private void buscar() {
        myRef = database.getReference( PATH_PRODUCTS );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() )
                {
                    for( DataSnapshot singleSnap : dataSnapshot.getChildren() )
                    {
                        if( singleSnap.exists() )
                        {
                            Product p = singleSnap.getValue( Product.class );
                            if( p.getNomEstab().equalsIgnoreCase(nombre) )
                            {
                                Log.i("Busqueda", "Encontré un producto" + p.getNomProducto());
                                productosxrestaurant.add(p);
                            }
                        }

                    }
                }

                initializeAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void initializeAdapter() {
        ProductAdapter pAdapter = new ProductAdapter( productosxrestaurant );
        listProductsxrestaurant.setAdapter(pAdapter);
    }
}
