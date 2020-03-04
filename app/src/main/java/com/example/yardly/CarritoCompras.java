package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras extends AppCompatActivity {
    List<Producto> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);
        mlista=findViewById(R.id.liscarrito);
        Producto p = new Producto();
        p.setNombre("Nombre producto");
        p.setDescripcion("Descripcion");
        p.setPrecio(123456);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        ProductosCarritoAdapter caAdapter = new ProductosCarritoAdapter(this, R.layout.carrito_adapter,mProjection);
        mlista.setAdapter(caAdapter);
    }
}
