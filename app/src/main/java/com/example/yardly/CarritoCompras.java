package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras extends AppCompatActivity {
    List<Producto> mProjection= new ArrayList<>();
    ListView mlista;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);
        mlista=findViewById(R.id.liscarrito);
        b1=findViewById(R.id.comfiCompra);
        Producto p = new Producto();
        p.setNombre("Nombre producto");
        p.setDescripcion("Descripcion");
        p.setPrecio(123456);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),Principal.class);
                startActivity(registro);
            }
        });
        ProductosCarritoAdapter caAdapter = new ProductosCarritoAdapter(this, R.layout.carrito_adapter,mProjection);
        mlista.setAdapter(caAdapter);
    }
}
